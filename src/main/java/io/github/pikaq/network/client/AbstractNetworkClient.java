package io.github.pikaq.network.client;

import io.github.pikaq.network.NetworkLocationEnum;

public abstract class AbstractNetworkClient implements NetworkClient {

	@Override
	public NetworkLocationEnum networkLocation() {
		return NetworkLocationEnum.CLIENT;
	}

}
