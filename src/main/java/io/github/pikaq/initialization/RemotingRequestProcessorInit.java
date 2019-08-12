package io.github.pikaq.initialization;

import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.initialization.support.Initable;
import io.github.pikaq.remoting.protocol.command.RequestCode;
import io.github.pikaq.remoting.protocol.command.RemoteCommandFactory;
import io.github.pikaq.remoting.server.PingRemotingRequestProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RemotingRequestProcessorInit implements Initable {

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void init() {
		log.info("RemotingRequestProcessorInit init.");
		RemoteCommandFactory factory = SingletonFactoy.get(RemoteCommandFactory.class);
		factory.registerHandler(RequestCode.HEART_BEAT_REQ.getCode(), new PingRemotingRequestProcessor());
	}

}
