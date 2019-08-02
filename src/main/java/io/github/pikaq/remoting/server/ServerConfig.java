package io.github.pikaq.remoting.server;

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
	private int listeningPort = 0;

	/**
	 * 读写空闲检测事件，默认为60秒，单位秒；0或者负数不启用
	 */
	private long allIdleTime = 60;
}
