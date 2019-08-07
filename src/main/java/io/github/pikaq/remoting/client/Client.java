package io.github.pikaq.remoting.client;

import java.util.concurrent.CompletableFuture;

import io.github.pikaq.remoting.RemoteClientException;
import io.github.pikaq.remoting.Remoteable;
import io.github.pikaq.remoting.RunningState;
import io.github.pikaq.remoting.protocol.command.RemoteCommand;

public interface Client extends Remoteable {

	void connect() throws RemoteClientException;
	
	RemoteCommand sendRequest(RemoteCommand request);
	
	CompletableFuture<RemoteCommand> sendAsyncRequest(RemoteCommand request);

	void setClientConfig(ClientConfig clientConfig);

	ClientConfig getClientConfig();

	String getClientName();

	void shutdown();

	RunningState runningState();

}
