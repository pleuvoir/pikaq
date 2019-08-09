package io.github.pikaq.remoting.protocol;

import io.github.pikaq.remoting.protocol.command.RemoteCommand;
import io.netty.channel.ChannelHandlerContext;

/**
 * 远程命令处理器
 * @author pleuvoir
 *
 * @param <REQ>
 * @param <RSP>
 */
public interface RemoteCommandProcessor<REQ extends RemoteCommand, RSP extends RemoteCommand> {

	RSP handler(ChannelHandlerContext ctx, REQ request);
}
