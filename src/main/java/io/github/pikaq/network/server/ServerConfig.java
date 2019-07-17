package io.github.pikaq.network.server;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServerConfig {

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
	private int serverOpenPort = -1;
}
