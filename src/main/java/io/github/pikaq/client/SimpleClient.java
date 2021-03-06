package io.github.pikaq.client;

import com.google.common.base.Stopwatch;
import io.github.pikaq.InvokeCallback;
import io.github.pikaq.RemotingAbstract;
import io.github.pikaq.RunningState;
import io.github.pikaq.URL;
import io.github.pikaq.common.exception.RemoteClientException;
import io.github.pikaq.common.exception.RemotingSendRequestException;
import io.github.pikaq.common.exception.RemotingTimeoutException;
import io.github.pikaq.common.util.MixUtils;
import io.github.pikaq.common.util.RemotingUtils;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.protocol.codec.RemoteCommandCodecHandler;
import io.github.pikaq.protocol.command.RemotingCommand;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

public class SimpleClient extends RemotingAbstract implements RemotingClient {

  private Bootstrap bootstrap;
  private NioEventLoopGroup eventLoopGroup;

  private final URL url;
  private ClientConfig clientConfig;

  protected GenericObjectPool pool;
  protected GenericObjectPool.Config poolConfig;
  protected PoolableObjectFactory factory;

  private ClientConnnectManager clientConnnectManager;
  private volatile RunningState runningState;

  public SimpleClient(ClientConfig clientConfig, URL url) {

    this.clientConfig = clientConfig;
    this.url = url;

    bootstrap = new Bootstrap();
    eventLoopGroup = new NioEventLoopGroup();

    bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, clientConfig.getConnectTimeoutMillis())
        .option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.TCP_NODELAY, true)
        .handler(new ChannelInitializer<SocketChannel>() {

          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 12, 4));
            ch.pipeline().addLast(SingletonFactoy.get(RemoteCommandCodecHandler.class));
            if (clientConfig.getHeartbeatIntervalSeconds() > 0) {
              ch.pipeline()
                  .addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS)); // 30秒没有读事件发送心跳到服务端
              ch.pipeline().addLast(new HealthyChecker(SimpleClient.this));
            }
            ch.pipeline().addLast(new NettyClientHandler());
          }
        });

    clientConnnectManager = new ClientConnnectManager(bootstrap);
    runningState = RunningState.WAITING;
  }

  @Override
  public void connectWithRetry(URL url) {
    Stopwatch stopwatch = Stopwatch.createStarted();
    logger.info("尝试连接：{}", url.toAddress());
    SocketAddress remoteAddress = RemotingUtils.string2SocketAddress(url.toAddress());
    ChannelFuture future = doConnectWithRetry(remoteAddress,
        clientConfig.getStartFailReconnectTimes());
    Channel channel = future.channel();
    clientConnnectManager.putChannel(channel);
    logger.info("客户端连接{}完成，耗时：{}ms", url.toAddress(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
  }

  @Override
  public void shutdown() {
    logger.info("shutdown byebye..");
    super.shutdown();
    if (eventLoopGroup != null) {
      eventLoopGroup.shutdownGracefully();
    }
    if (clientConnnectManager != null) {
      clientConnnectManager.release();
    }
  }

  @Override
  public RunningState runningState() {
    return runningState;
  }

  @Override
  public URL getURL() {
    return this.url;
  }

  protected ChannelFuture doConnectWithRetry(SocketAddress remoteAddress, int retryTimes) {
    ChannelFuture future = bootstrap.connect(remoteAddress);
    future.awaitUninterruptibly(); // connect不可以使用sync会报错
    if (future.isSuccess()) {
      ChannelFuture f = (ChannelFuture) future;
      logger.info("客户端已连接远程节点：[{}]", f.channel().remoteAddress());
      return f;
    } else if (retryTimes == 0) {
      logger.error("客户端连接远程节点{}：尝试重连到达上限，不再进行连接。原因：{}", remoteAddress, future.cause().getMessage());
      throw new RemoteClientException(future.cause().getMessage());
    } else {
      // 第几次重连
      int sequence = clientConfig.getStartFailReconnectTimes() - retryTimes + 1;
      int delay = 1 << sequence;
      logger.warn("客户端连接远程节点第{}次连接失败，{} {}秒后尝试重试。", sequence, future.cause().getMessage(), delay);
      MixUtils.sleep(delay);
      return doConnectWithRetry(remoteAddress, retryTimes - 1);
    }
  }

  @Override
  public RemotingCommand invokeSync(URL url, RemotingCommand request, long timeoutMillis)
      throws RemotingTimeoutException, RemotingSendRequestException {
    Channel channel = clientConnnectManager.getOrCreateChannel(url.toAddress());
    return super.invokeSyncImpl(channel, request.clone(), timeoutMillis);
  }

  @Override
  public void invokeAsync(URL url, RemotingCommand request, InvokeCallback invokeCallback)
      throws RemotingSendRequestException {
    Channel channel = clientConnnectManager.getOrCreateChannel(url.toAddress());
    super.invokeAsyncImpl(channel, request.clone(), invokeCallback);
  }

  @Override
  public void invokeOneWay(URL url, RemotingCommand request)
      throws RemotingSendRequestException {
    Channel channel = clientConnnectManager.getOrCreateChannel(url.toAddress());
    super.invokeOnewayImpl(channel, request.clone());
  }


  /**
   * 处理客户端收到的请求
   */
  class NettyClientHandler extends SimpleChannelInboundHandler<RemotingCommand> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RemotingCommand msg) throws Exception {
      logger.info(" -=-=-=-=-= processMessageReceived -=-=-=-=-= {}", msg.toJSON());
      processMessageReceived(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      ctx.close();
      logger.error("ctx close,cause:", cause);
    }
  }

  @Override
  public ClientConfig getClientConfig() {
    return clientConfig;
  }

}
