package io.github.pikaq.remoting.client;

import com.google.common.base.Preconditions;

public class ClientConfig {

	private final String host;

	private final int port;

	private final int startFailReconnectTimes;

	private final int connectTimeoutMillis;

	private ClientConfig(ClientConfigBuilder builder) {
		this.host = builder.host;
		this.port = builder.port;
		this.startFailReconnectTimes = builder.startFailReconnectTimes;
		this.connectTimeoutMillis = builder.connectTimeoutMillis;
	}

	public static ClientConfigBuilder create(String host, int port) {
		return new ClientConfigBuilder(host, port);
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public int getStartFailReconnectTimes() {
		return startFailReconnectTimes;
	}

	public int getConnectTimeoutMillis() {
		return connectTimeoutMillis;
	}

	public static class ClientConfigBuilder {
		private final String host;

		private final int port;

		private int startFailReconnectTimes = 3;

		private int connectTimeoutMillis = 5000;

		public ClientConfigBuilder(String host, int port) {
			this.host = host;
			this.port = port;
		}

		public ClientConfigBuilder startFailReconnectTimes(int startFailReconnectTimes) {
			this.startFailReconnectTimes = startFailReconnectTimes;
			return this;
		}

		public ClientConfigBuilder connectTimeoutMillis(int connectTimeoutMillis) {
			this.connectTimeoutMillis = connectTimeoutMillis;
			return this;
		}

		public ClientConfig build() {
			ClientConfig clientConfig = new ClientConfig(this);
			validate(clientConfig);
			return clientConfig;
		}

		private void validate(ClientConfig clientConfig) {
			Preconditions.checkNotNull(clientConfig, "未设置服务端配置");
			Preconditions.checkArgument(clientConfig.getStartFailReconnectTimes() > 0, "启动连接重试次数不能为为负");
			Preconditions.checkArgument(clientConfig.getConnectTimeoutMillis() > 0, "连接超时时间不能为为负");
		}
	}

}
