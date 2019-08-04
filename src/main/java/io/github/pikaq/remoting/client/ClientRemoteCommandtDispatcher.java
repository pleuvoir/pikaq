package io.github.pikaq.remoting.client;

import io.github.pikaq.remoting.protocol.RemoteCommandHandler;
import io.github.pikaq.remoting.protocol.command.DefaultRemoteCommandFactory;
import io.github.pikaq.remoting.protocol.command.RemoteCommand;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端远程命令分发器
 * 
 * <p>
 * 所有命令使用此处理器处理
 * 
 * @author pleuvoir
 *
 */
@Slf4j
public class ClientRemoteCommandtDispatcher extends SimpleChannelInboundHandler<RemoteCommand> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RemoteCommand command) throws Exception {
		log.info("ClientRemoteCommandtDispatcher received {}", command.toJSON());
		int symbol = command.getSymbol();

		RemoteCommandHandler handler = DefaultRemoteCommandFactory.INSTANCE.select(symbol);
		if (handler == null) {
			log.error("handler is null ignore.");
			return;
		}
		handler.handler(ctx, command);
	}

}
