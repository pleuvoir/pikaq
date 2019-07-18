package io.github.pikaq.network.server;

import io.github.pikaq.network.RemotingContext;

public class DefaultServer extends AbstractServer {

	private String serverName;

	public DefaultServer(String serverName) {
		this.serverName = serverName;
	}

	@Override
	public String getServerName() {
		return this.serverName;
	}

	@Override
	protected void doStart(RemotingContext context) {

	}

	@Override
	protected void doClose() {

	}

}
