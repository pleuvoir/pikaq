package io.github.pikaq.network.client;

import io.github.pikaq.network.Networkable;
import io.github.pikaq.network.RemotingContext;

public interface Client extends Networkable {

	void connect(ClientConfig clientConfig);

	void reconnect(RemotingContext clientContext);

	void disconnect(RemotingContext clientContext);
}
