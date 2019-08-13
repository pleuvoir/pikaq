package io.github.pikaq;

import io.github.pikaq.protocol.command.RemotingCommand;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "create")
public class RemoteInvokerContext {

	private ChannelHandlerContext ctx;
	private RemotingCommand request;

}
