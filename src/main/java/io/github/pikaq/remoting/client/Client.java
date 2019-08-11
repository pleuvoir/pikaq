package io.github.pikaq.remoting.client;

import java.util.concurrent.CompletableFuture;

import io.github.pikaq.remoting.RemoteClientException;
import io.github.pikaq.remoting.RemotingSendRequestException;
import io.github.pikaq.remoting.RunningState;
import io.github.pikaq.remoting.protocol.command.RemotingCommand;

public interface Client {

	void connect() throws RemoteClientException;

	RemotingCommand sendRequest(RemotingCommand request) throws RemotingSendRequestException;
	
	void sendOneWay(RemotingCommand request)throws RemotingSendRequestException ;

	CompletableFuture<RemotingCommand> sendAsyncRequest(RemotingCommand request) throws RemotingSendRequestException;

	void setClientConfig(ClientConfig clientConfig);

	ClientConfig getClientConfig();

	String getClientName();

	void shutdown();

	RunningState runningState();

}
