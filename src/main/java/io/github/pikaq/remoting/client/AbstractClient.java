package io.github.pikaq.remoting.client;

import io.github.pikaq.remoting.RemoteLocationEnum;

public abstract class AbstractClient implements Client {

	@Override
	public RemoteLocationEnum remoteLocation() {
		return RemoteLocationEnum.CLIENT;
	}

}
