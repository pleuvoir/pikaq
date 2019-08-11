package io.github.pikaq.remoting;

import io.github.pikaq.remoting.protocol.command.RemotingCommand;
import io.netty.channel.ChannelHandlerContext;

/**
 * 请求处理器
 * @author pleuvoir
 *
 */
public interface RemoteRequestProcessor {
	
	RemotingCommand handler(ChannelHandlerContext ctx, RemotingCommand request);
	
}
