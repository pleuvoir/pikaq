package io.github.pikaq.remoting.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.UntypedActor;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.remoting.RemoteInvokerContext;
import io.github.pikaq.remoting.protocol.RemoteCommandProcessor;
import io.github.pikaq.remoting.protocol.command.CarrierCommand;
import io.github.pikaq.remoting.protocol.command.RemoteCommand;
import io.github.pikaq.remoting.protocol.command.RemoteCommandFactory;
import io.netty.channel.ChannelHandlerContext;

public class DispatcherActor extends UntypedActor {

	private static Logger logger = LoggerFactory.getLogger(DispatcherActor.class);

	public DispatcherActor() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onReceive(Object msg) throws Throwable {
		logger.info("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

		RemoteInvokerContext invokerContext = (RemoteInvokerContext) msg;

		RemoteCommand request = invokerContext.getRequest();
		ChannelHandlerContext ctx = invokerContext.getCtx();
		
		RemoteCommandProcessor<RemoteCommand, RemoteCommand> processor = SingletonFactoy.get(RemoteCommandFactory.class)
				.select(request.getSymbol());
		
		RemoteCommand response = null;

		if (processor == null) {
			response = CarrierCommand.buildString(true, "server empty processor", "OK");
		} else {
			response = processor.handler(ctx, request);
			assert response != null;
		}

		response.setId(request.getId());

		getSender().tell(response, getSelf());
	}

}
