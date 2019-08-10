package io.github.pikaq.remoting.server;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.Mapper;
import akka.dispatch.OnFailure;
import akka.dispatch.OnSuccess;
import akka.pattern.Patterns;
import akka.util.Timeout;
import io.github.pikaq.PikaqConst;
import io.github.pikaq.common.annotation.ServerSide;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.remoting.RemoteInvokerContext;
import io.github.pikaq.remoting.protocol.command.RemoteCommand;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import scala.concurrent.Future;

@ServerSide
@ChannelHandler.Sharable
public class ServerRemoteCommandtDispatcher extends SimpleChannelInboundHandler<RemoteCommand> {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RemoteCommand request) throws Exception {

		//DispatcherActor不能通过new的方式创建
		RemoteInvokerContext invokerContext = RemoteInvokerContext.create()
				.ctx(ctx)
				.request(request)
				.build();

		ActorSystem system = SingletonFactoy.get(ActorSystem.class);
		ActorRef actorRef = system.actorOf(Props.create(DispatcherActor.class));
		// 业务超时时间
		Timeout timeout = Timeout.apply(PikaqConst.OPT_TIMEOUT, TimeUnit.SECONDS);

		Future<RemoteCommand> future = Patterns.ask(actorRef, invokerContext, timeout)
				.map(new Mapper<Object, RemoteCommand>() {
					@Override
					public RemoteCommand apply(Object parameter) {
						return (RemoteCommand) parameter;
					}
				}, system.dispatcher());

		// 成功回调
		future.onSuccess(new OnSuccess<RemoteCommand>() {
			@Override
			public void onSuccess(RemoteCommand response) throws Throwable {
				if (request.isResponsible()) {
					response.setId(request.getId());
					ctx.writeAndFlush(response);
					logger.debug("[服务端分发器]响应命令处理结束：request={}，response={}", request.toJSON(), response.toJSON());
				}
				system.stop(actorRef);
			}
		}, system.dispatcher());

		// 失败回调
		future.onFailure(new OnFailure() {
			@Override
			public void onFailure(Throwable e) throws Throwable {
				logger.error(e.getMessage(), e);
				system.stop(actorRef);
			}
		}, system.dispatcher());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.channel().close();
	}

}
