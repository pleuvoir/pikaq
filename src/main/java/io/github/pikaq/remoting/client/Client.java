package io.github.pikaq.remoting.client;

import io.github.pikaq.remoting.Remoteable;
import io.github.pikaq.remoting.RemotingContext;

public interface Client extends Remoteable {

	void connect(ClientConfig clientConfig);

	void reconnect(RemotingContext clientContext);

	void disconnect(RemotingContext clientContext);
}
