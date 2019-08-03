package io.github.pikaq.remoting.protocol.command;

import java.util.Map;

import io.github.pikaq.common.util.ToJSON;

/**
 * 远程命令接口
 * @author pleuvoir
 *
 */
public interface RemoteCommand extends ToJSON{

	String requestId();
	
	/**
	 * 获取命令指令枚举
	 */
	CommandCode getCommandCode();

	/**
	 * 命令码类型 SYSTEM, RPC, USER
	 */
	CommandCodeType commandCodeType();
	
	Map<String, Object> getAttachs();
	
	void setAttachs(String key, Object value);
	
	/**
	 * 是否客户端请求
	 */
	boolean isRequest();

}
