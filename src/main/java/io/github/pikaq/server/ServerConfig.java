package io.github.pikaq.server;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServerConfig {
	

	public ServerConfig(int listeningPort) {
		this.listeningPort = listeningPort;
	}

	public ServerConfig() {
		super();
	}

	/**
	 * Socket连接缓冲队列大小，默认为1024
	 */
	private int soBacklog = 1024;

	/**
	 * 公共线程池线程数，默认为CPU数
	 */
	private int publicThreadPoolNums = Runtime.getRuntime().availableProcessors();

	/**
	 * 远程服务开放端口
	 */
	private int listeningPort = 0;

	/**
	 * 读写空闲检测超时时间，默认为120秒，单位秒；0或者负数不启用<br>
	 * 当120秒未接收到读写请求时触发事件
	 */
	private long allIdleTime = 120;
	
	/**
	 * 业务超时时间（秒），默认5秒
	 */
	private Integer optTimeout = 5;
}
