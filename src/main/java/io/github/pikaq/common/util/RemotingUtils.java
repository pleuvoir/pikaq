package io.github.pikaq.common.util;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Preconditions;
import io.github.pikaq.URL;
import io.github.pikaq.server.ServerConfig;
import io.netty.channel.Channel;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import org.apache.commons.lang3.StringUtils;

public class RemotingUtils {

  /**
   * 转换为远程地址
   */
  public static String parseChannelRemoteAddr(final Channel channel) {
    if (null == channel) {
      return StringUtils.EMPTY;
    }
    SocketAddress remote = channel.remoteAddress();
    final String addr = remote != null ? remote.toString() : "";

    if (addr.length() > 0) {
      int index = addr.lastIndexOf("/");
      if (index >= 0) {
        return addr.substring(index + 1);
      }

      return addr;
    }
    return StringUtils.EMPTY;
  }


  public static URL parseToURL(final Channel channel) {
    return parseToURL(parseChannelRemoteAddr(channel));
  }

  public static URL parseToURL(String address) {
    String[] s = address.split(":");
    return new URL(s[0], Integer.parseInt(s[1]));
  }

  /**
   * 转换为远程连接地址
   */
  public static SocketAddress string2SocketAddress(final String addr) {
    String[] s = addr.split(":");
    InetSocketAddress isa = new InetSocketAddress(s[0], Integer.parseInt(s[1]));
    return isa;
  }

  /**
   * 验证服务端配置
   */
  public static void validate(ServerConfig serverConfig) throws IllegalArgumentException {
    Preconditions.checkNotNull(serverConfig, "未设置服务端配置");
    int soBacklog = serverConfig.getSoBacklog();
    checkArgument(soBacklog > 0, "Socket连接缓冲队列大小配置错误，必须大于0");
    int publicThreadPoolNums = serverConfig.getPublicThreadPoolNums();
    Preconditions.checkArgument(publicThreadPoolNums > 0, "公共线程池数配置错误，必须大于0");
    int listeningPort = serverConfig.getListeningPort();
    checkArgument(listeningPort > 0, "监听放端口配置错误，请设置值");
    checkArgument(PortUtils.checkPortAvailable(listeningPort), listeningPort + "端口被占用，请重新配置。");
  }
}
