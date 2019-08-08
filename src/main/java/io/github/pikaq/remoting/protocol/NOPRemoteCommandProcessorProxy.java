package io.github.pikaq.remoting.protocol;

import io.github.pikaq.remoting.protocol.command.RemoteCommand;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NOPRemoteCommandProcessorProxy implements RemoteCommandProcessorProxy {

	@Override
	public void deBeforeRequest(RemoteCommand request) {
		log.info("deBeforeRequest - request：{}", request.toJSON());
	}

	@Override
	public void deAfterRequest(RemoteCommand request) {
		log.info("deAfterRequest - request：{}", request.toJSON());
	}

}
