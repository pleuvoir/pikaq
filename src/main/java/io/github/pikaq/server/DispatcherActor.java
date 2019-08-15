package io.github.pikaq.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.UntypedActor;
import io.github.pikaq.RemoteInvokerContext;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.protocol.RemotingRequestProcessor;
import io.github.pikaq.protocol.command.RemoteCommandFactory;
import io.github.pikaq.protocol.command.RemotingCommand;
import io.github.pikaq.protocol.command.embed.CarrierCommand;
import io.netty.channel.ChannelHandlerContext;

public class DispatcherActor extends UntypedActor {

	private static Logger logger = LoggerFactory.getLogger(DispatcherActor.class);

	public DispatcherActor() {
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
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
			
			//如果没有处理器，返回一个提示
			logger.warn("processor is null, request code = {}", request.getRequestCode());
			
			String tips ="processor is null, request code = %s, message From = %s" ;
			
			response = CarrierCommand.builder()
					.success(false)
					.message(String.format(tips, request.getRequestCode(), request.getMessageFrom()))
					.build();
			response.setResponsible(false);
			response.markResponse();

		} else {
			logger.debug("processor=<{}>, request={}", processor.getClass().getCanonicalName(), request.toJSON());
			response = processor.handler(ctx, request);
			assert response != null;
		}

		response.setMessageId(request.getMessageId());

		getSender().tell(response, getSelf());

		logger.info("tell {}=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=", response.toJSON());
	}

}
