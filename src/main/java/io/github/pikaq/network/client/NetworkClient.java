package io.github.pikaq.network.client;

import io.github.pikaq.network.Networkable;

public interface NetworkClient extends Networkable {

	void connect(ClientConfig clientConfig);

	void reconnect(NetworkClientContext clientContext);

	void disconnect(NetworkClientContext clientContext);
}
