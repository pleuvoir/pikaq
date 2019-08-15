package io.github.pikaq.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.UntypedActor;
import io.github.pikaq.RemoteInvokerContext;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.protocol.RemotingRequestProcessor;
import io.github.pikaq.protocol.command.CarrierCommand;
import io.github.pikaq.protocol.command.RemoteCommandFactory;
import io.github.pikaq.protocol.command.RemotingCommand;
import io.netty.channel.ChannelHandlerContext;

public class DispatcherActor extends UntypedActor {

	private static Logger logger = LoggerFactory.getLogger(DispatcherActor.class);

	public DispatcherActor() {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onReceive(Object msg) throws Throwable {
		logger.info("onReceive =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

		RemoteInvokerContext invokerContext = (RemoteInvokerContext) msg;

		RemotingCommand request = invokerContext.getRequest();
		ChannelHandlerContext ctx = invokerContext.getCtx();

		RemotingRequestProcessor processor = SingletonFactoy.get(RemoteCommandFactory.class)
				.select(request.getRequestCode());

		RemotingCommand response = null;

		if (processor == null) {
			logger.info("processor is null, request code = {}", request.getRequestCode());

			response = CarrierCommand.buildString(false,
					"processor is null, request code = " + request.getRequestCode(), null);
			response.setResponsible(false);

		} else {
			response = processor.handler(ctx, request);
			assert response != null;
		}

		response.setMessageId(request.getMessageId());

		getSender().tell(response, getSelf());

		logger.info("tell =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
	}

}
