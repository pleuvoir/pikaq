package io.github.pikaq.remoting.client;

import com.google.common.base.Preconditions;

public class ClientConfig {

	private final String host;

	private final int port;

	private final int startFailReconnectTimes;

	private final int connectTimeoutMillis;

	private final Boolean devMode;

	/**
	 * 心跳间隔，一般设置为空闲检测时间的1/3，默认为10秒
	 */
	public final int heartbeatIntervalSeconds;

	/**
	 * 读空闲检测事件，超过后触发事件
	 */
	public final long readerIdleTime;

	/**
	 * 写空闲检测事件，超过后触发事件
	 */
	public final long writerIdleTime;

	private ClientConfig(ClientConfigBuilder builder) {
		this.host = builder.host;
		this.port = builder.port;
		this.startFailReconnectTimes = builder.startFailReconnectTimes;
		this.connectTimeoutMillis = builder.connectTimeoutMillis;
		this.heartbeatIntervalSeconds = builder.heartbeatIntervalSeconds;
		this.readerIdleTime = builder.readerIdleTime;
		this.writerIdleTime = builder.writerIdleTime;
		this.devMode = builder.devMode;
	}

	public static ClientConfigBuilder create(String host, int port) {
		return new ClientConfigBuilder(host, port);
	}

	public Boolean getDevMode() {
		return devMode;
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

	public int getHeartbeatIntervalSeconds() {
		return heartbeatIntervalSeconds;
	}

	public static class ClientConfigBuilder {
		private final String host;

		private final int port;

		private int startFailReconnectTimes = 3;

		private int connectTimeoutMillis = 5000;

		public int heartbeatIntervalSeconds = 10;

		/**
		 * 读空闲检测事件，超过后触发事件，默认30秒
		 */
		public long readerIdleTime = 30;

		/**
		 * 写空闲检测事件，超过后触发事件，默认30秒
		 */
		public long writerIdleTime = 30;

		private Boolean devMode = false;

		public ClientConfigBuilder(String host, int port) {
			this.host = host;
			this.port = port;
		}

		public ClientConfigBuilder writerIdleTime(long writerIdleTime) {
			this.writerIdleTime = writerIdleTime;
			return this;
		}

		public ClientConfigBuilder readerIdleTime(long readerIdleTime) {
			this.readerIdleTime = readerIdleTime;
			return this;
		}

		public ClientConfigBuilder devMode(Boolean devMode) {
			this.devMode = devMode;
			return this;
		}

		public ClientConfigBuilder startFailReconnectTimes(int startFailReconnectTimes) {
			this.startFailReconnectTimes = startFailReconnectTimes;
			return this;
		}

		public ClientConfigBuilder connectTimeoutMillis(int connectTimeoutMillis) {
			this.connectTimeoutMillis = connectTimeoutMillis;
			return this;
		}

		public ClientConfigBuilder heartbeatIntervalSeconds(int heartbeatIntervalSeconds) {
			this.heartbeatIntervalSeconds = heartbeatIntervalSeconds;
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
			Preconditions.checkArgument(clientConfig.getHeartbeatIntervalSeconds() > 0, "心跳间隔时间不能为为负");
		}
	}

}
