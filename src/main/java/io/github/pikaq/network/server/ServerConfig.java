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

}
