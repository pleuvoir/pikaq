package io.github.pikaq.client;

import com.google.common.base.Preconditions;

public class ClientConfig {

	private final int startFailReconnectTimes;

	private final int connectTimeoutMillis;

	/**
	 * 心跳间隔，一般设置为空闲检测时间的1/3，默认为30秒，即30秒发送一次心跳<br>
	 * 可以设置为0，为0则关闭心跳检测以及长连接短线重连
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
		this.startFailReconnectTimes = builder.startFailReconnectTimes;
		this.connectTimeoutMillis = builder.connectTimeoutMillis;
		this.heartbeatIntervalSeconds = builder.heartbeatIntervalSeconds;
		this.readerIdleTime = builder.readerIdleTime;
		this.writerIdleTime = builder.writerIdleTime;
	}

	public static ClientConfigBuilder create() {
		return new ClientConfigBuilder();
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

		private int startFailReconnectTimes = 3;

		private int connectTimeoutMillis = 5000;

		public int heartbeatIntervalSeconds = 30;

		/**
		 * 读空闲检测事件，超过后触发事件，默认60秒
		 */
		public long readerIdleTime = 90;

		/**
		 * 写空闲检测事件，超过后触发事件，默认90秒
		 */
		public long writerIdleTime = 30;

		public ClientConfigBuilder() {
		}

		public ClientConfigBuilder writerIdleTime(long writerIdleTime) {
			this.writerIdleTime = writerIdleTime;
			return this;
		}

		public ClientConfigBuilder readerIdleTime(long readerIdleTime) {
			this.readerIdleTime = readerIdleTime;
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
			Preconditions.checkNotNull(clientConfig, "未设置客户端配置");
			Preconditions.checkArgument(clientConfig.getStartFailReconnectTimes() > 0, "启动连接重试次数不能为为负");
			Preconditions.checkArgument(clientConfig.getConnectTimeoutMillis() > 0, "连接超时时间不能为为负");
		}
	}

}
