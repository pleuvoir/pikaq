package io.github.pikaq.remoting.protocol.command;

/**
 * 远程命令工厂，构建远程命令（通常是发送给对端的数据对象）
 * @author pleuvoir
 *
 */
public interface RemoteCommandFactory {
	
	void load(String scannerPath) throws RemoteCommandException;

	/**
	 * 根据指令编码构建一个远程命令
	 * @param code 指令编码
	 */
	RemoteCommand newRemoteCommand(CommandCode code) throws RemoteCommandException;

	/**
	 * 根据指令编码还原远程命令
	 * @param code  指令编码
	 */
	Class<? extends RemoteCommand> fromCommandCode(CommandCode code) throws RemoteCommandException;
	
	/**
	 * 请求命令转为响应命令
	 */
	RemoteCommand convertConvert2Response(RemoteCommand request)  throws RemoteCommandException;
	
	/**
	 * 请求指令转为响应命令
	 */
	RemoteCommand convertConvert2Response(CommandCode code)  throws RemoteCommandException;
	
}
