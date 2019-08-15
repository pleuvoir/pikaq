package io.github.pikaq.initialization;


import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.initialization.support.Initable;
import io.github.pikaq.protocol.CarrierCompositeProcessor;
import io.github.pikaq.protocol.command.RemoteCommandFactory;
import io.github.pikaq.protocol.command.RequestCode;
import io.github.pikaq.server.PingRequestProcessor;
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
		//服务端处理心跳
		factory.registerHandler(RequestCode.HEART_BEAT_REQ.getCode(), new PingRequestProcessor());
		//处理托架命令
		factory.registerHandler(RequestCode.CARRIER.getCode(), new CarrierCompositeProcessor());
	}

}
