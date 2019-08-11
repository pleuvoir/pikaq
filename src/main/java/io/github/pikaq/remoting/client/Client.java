package io.github.pikaq.remoting.client;

import java.util.concurrent.CompletableFuture;

import io.github.pikaq.remoting.RemoteClientException;
import io.github.pikaq.remoting.RemoteSendException;
import io.github.pikaq.remoting.RunningState;
import io.github.pikaq.remoting.protocol.command.RemotingCommand;

public interface Client {

	void connect() throws RemoteClientException;

	RemotingCommand sendRequest(RemotingCommand request) throws RemoteSendException;
	
	void sendOneWay(RemotingCommand request)throws RemoteSendException ;

	CompletableFuture<RemotingCommand> sendAsyncRequest(RemotingCommand request) throws RemoteSendException;

	void setClientConfig(ClientConfig clientConfig);

	ClientConfig getClientConfig();

	String getClientName();

	void shutdown();

	RunningState runningState();

}
