package io.github.pikaq;

public interface PikaqConst {

	String SERIALIZER = "pikaq.serializer.algorithm";

	/**
	 * 命令包扫描路径
	 */
	String COMMAND_SCANNER_PATH = "io.github.pikaq.remoting.protocol.command";
	
	String ALL_SCANNER_PATH = ".*";

	/**
	 * 默认的等待超时时间5秒，<b>注意，该时间指的是从发送出去报文到处理完异步响应的等待时间</b>
	 */
	long DEFAULT_SEND_TIMEOUT_MS = 5000L;
	
	/**
	 * 在AKKA中处理的超时时间
	 */
	Integer OPT_TIMEOUT = 3;

}
