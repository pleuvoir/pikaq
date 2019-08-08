package io.github.pikaq.basic;

import io.github.pikaq.remoting.protocol.RemoteCommandProcessorProxy;
import io.github.pikaq.remoting.protocol.command.RemoteCommand;

public class NOPRemoteCommandProcessorProxy implements RemoteCommandProcessorProxy {

	@Override
	public void deBeforeRequest(RemoteCommand request) {
		System.out.println("处理前");
	}

	@Override
	public void deAfterRequest(RemoteCommand request) {
		System.out.println("处理后");
	}

}
