package io.github.pikaq.remoting.protocol;

import io.github.pikaq.remoting.protocol.command.RemotingCommand;
import io.netty.channel.ChannelHandlerContext;

/**
 * 远程命令处理器
 * @author pleuvoir
 *
 * @param <REQ>
 * @param <RSP>
 */
public interface RemoteCommandProcessor<REQ extends RemotingCommand, RSP extends RemotingCommand> {

	RSP handler(ChannelHandlerContext ctx, REQ request);
}
