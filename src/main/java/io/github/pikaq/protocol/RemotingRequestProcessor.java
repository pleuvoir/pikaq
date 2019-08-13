package io.github.pikaq.protocol;

import io.github.pikaq.protocol.command.RemotingCommand;
import io.netty.channel.ChannelHandlerContext;

/**
 * 远程命令处理器
 * @author pleuvoir
 *
 * @param <REQ>
 * @param <RSP>
 */
public interface RemotingRequestProcessor<REQ extends RemotingCommand, RSP extends RemotingCommand> {

	RSP handler(ChannelHandlerContext ctx, REQ request);
}
