package io.github.pikaq.remoting;

import io.github.pikaq.remoting.protocol.Packet;

/**
 * 远程命令接口
 * @author pleuvoir
 *
 */
public interface RemoteCommand<P extends Packet> {

	String id();
	
	/**
	 * 获取命令编码枚举
	 */
	CommandCode getCommandCode();

	/**
	 * 命令码类型 SYSTEM, RPC, USER
	 */
	CommandCodeType commandCodeType();

	/**
	 * 获取对应的包对象，该对象会被冲刷到远程节点
	 */
	P getPacket();

	/**
	 * 获取包实现类
	 */
	Class<P> getPacketClazz();

	/**
	 * 是否请求
	 */
	boolean isRequest();

}
