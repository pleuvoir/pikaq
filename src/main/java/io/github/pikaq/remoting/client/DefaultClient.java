package io.github.pikaq.remoting.client;

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

}
