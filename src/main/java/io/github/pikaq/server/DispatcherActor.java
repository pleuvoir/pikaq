package io.github.pikaq.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.UntypedActor;
import io.github.pikaq.RemoteInvokerContext;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.protocol.RemotingCommandType;
import io.github.pikaq.protocol.RemotingRequestProcessor;
import io.github.pikaq.protocol.command.RemoteCommandFactory;
import io.github.pikaq.protocol.command.RemotingCommand;
import io.github.pikaq.protocol.command.RequestCode;
import io.github.pikaq.protocol.command.body.CarrierCommandBody;
import io.netty.channel.ChannelHandlerContext;

public class DispatcherActor extends UntypedActor {

	private static Logger logger = LoggerFactory.getLogger(DispatcherActor.class);

	public DispatcherActor() {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onReceive(Object msg) throws Throwable {
		logger.info("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

		RemoteInvokerContext invokerContext = (RemoteInvokerContext) msg;

		RemotingCommand request = invokerContext.getRequest();
		ChannelHandlerContext ctx = invokerContext.getCtx();
		
		 RemotingRequestProcessor processor = SingletonFactoy.get(RemoteCommandFactory.class)
				.select(request.getRequestCode());
		
		RemotingCommand response = null;

		if (processor == null) {
			response = new RemotingCommand();
			response.setResponsible(false);
			response.setRequestCode(RequestCode.CARRIER.getCode());
			response.setCommandType(RemotingCommandType.RESPONSE_COMMAND);
			response.setBody(CarrierCommandBody.buildString(true, "server empty processor", "OK"));
			
		} else {
			response = processor.handler(ctx, request);
			assert response != null;
		}

		response.setMessageId(request.getMessageId());

		getSender().tell(response, getSelf());
	}

}
