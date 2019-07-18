package io.github.pikaq.network.client;

import io.github.pikaq.network.NetworkLocationEnum;

public abstract class AbstractClient implements Client {

	@Override
	public NetworkLocationEnum networkLocation() {
		return NetworkLocationEnum.CLIENT;
	}

}
