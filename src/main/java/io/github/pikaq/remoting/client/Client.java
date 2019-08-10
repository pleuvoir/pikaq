package io.github.pikaq.remoting.client;

import java.util.concurrent.CompletableFuture;

import io.github.pikaq.remoting.RemoteClientException;
import io.github.pikaq.remoting.RemoteSendException;
import io.github.pikaq.remoting.RunningState;
import io.github.pikaq.remoting.protocol.command.RemoteCommand;

public interface Client {

	void connect() throws RemoteClientException;

	RemoteCommand sendRequest(RemoteCommand request) throws RemoteSendException;
	
	void sendOneWay(RemoteCommand request)throws RemoteSendException ;

	CompletableFuture<RemoteCommand> sendAsyncRequest(RemoteCommand request) throws RemoteSendException;

	void setClientConfig(ClientConfig clientConfig);

	ClientConfig getClientConfig();

	String getClientName();

	void shutdown();

	RunningState runningState();

}
