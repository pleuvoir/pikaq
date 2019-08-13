package io.github.pikaq.server;


import akka.actor.UntypedActor;
import io.github.pikaq.RemoteInvokerContext;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.protocol.RemotingRequestProcessor;
import io.github.pikaq.protocol.command.CarrierCommand;
import io.github.pikaq.protocol.command.RemoteCommandFactory;
import io.github.pikaq.protocol.command.RemotingCommand;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
			response = CarrierCommand.buildString(true, "server empty processor", "OK");
		} else {
			response = processor.handler(ctx, request);
			assert response != null;
		}

		response.setMessageId(request.getMessageId());

		getSender().tell(response, getSelf());
	}

}
