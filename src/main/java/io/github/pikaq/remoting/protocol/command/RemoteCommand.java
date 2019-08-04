package io.github.pikaq.remoting.protocol.command;

import java.util.Map;

import io.github.pikaq.common.util.ToJSON;

/**
 * 远程命令
 * @author pleuvoir
 *
 */
public abstract class RemoteCommand implements ToJSON {

	/**
	 * 请求唯一ID
	 */
	public abstract String getRequestId();

	/**
	 * 获取命令指令
	 */
	public abstract int getSymbol();

	/**
	 * 命令码类型 SYSTEM, RPC, USER
	 */
	public abstract CommandCodeType getCommandCodeType();

	public abstract Map<String, Object> getAttachs();

	/**
	 * 附加参数，对象中不定义属性时可使用
	 */
	public abstract void setAttachs(String key, Object value);

}
