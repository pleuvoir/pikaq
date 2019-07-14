package io.github.pikaq.network.server;

import io.github.pikaq.network.NetworkLocationEnum;

public abstract class AbstractNetworkServer implements NetworkServer {

	@Override
	public NetworkLocationEnum networkLocation() {
		return NetworkLocationEnum.SERVER;
	}

}
