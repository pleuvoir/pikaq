package io.github.pikaq.remoting.client;

import io.github.pikaq.remoting.RemotingContext;

public class DefaultClient extends AbstractClient {

	private String name;

	public DefaultClient(String name) {
		this.name = name;
	}

	@Override
	public String getClientName() {
		return name;
	}

	@Override
	protected void doClose() {

	}

	@Override
	protected void doStart(RemotingContext remotingContext) {

	}

}
