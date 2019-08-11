package io.github.pikaq.basic;

import io.github.pikaq.initialization.helper.CommandProcessorInitAdapter;
import io.github.pikaq.remoting.protocol.RemoteCommandProcessor;
import io.github.pikaq.remoting.protocol.command.RemotingCommand;
import io.netty.channel.ChannelHandlerContext;

public class UserCommandInitTest extends CommandProcessorInitAdapter {

	@Override
	public void init() {
		registerHandler(443, new RemoteCommandProcessor<RemotingCommand, RemotingCommand>() {
			@Override
			public RemotingCommand handler(ChannelHandlerContext ctx, RemotingCommand request) {
				System.out.println("走着");
				return null;
			}
		});
		
		registerHandler(444, new RemoteCommandProcessor<RemotingCommand, RemotingCommand>() {
			@Override
			public RemotingCommand handler(ChannelHandlerContext ctx, RemotingCommand request) {
				System.out.println("~");
				return null;
			}
		});
	}

}
