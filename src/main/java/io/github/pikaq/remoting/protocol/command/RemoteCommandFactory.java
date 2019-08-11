package io.github.pikaq.remoting.protocol.command;

import io.github.pikaq.remoting.protocol.RemoteCommandProcessor;

/**
 * 远程命令工厂
 * 
 * <p>构建远程命令（通常是发送给对端的数据对象）
 * 
 * @author pleuvoir
 *
 */
@SuppressWarnings("rawtypes")
public interface RemoteCommandFactory {
	
	void load(String scannerPath) throws RemoteCommandException;

	/**
	 * 根据指令编码构建一个远程命令
	 * @param code 指令编码
	 */
	RemotingCommand newRemoteCommand(CommandCode code) throws RemoteCommandException;

	/**
	 * 根据指令编码还原远程命令
	 * @param code  指令编码
	 */
	Class<? extends RemotingCommand> fromSymbol(int symbol) throws RemoteCommandException;
	
	
	/**
	 * 注册远程命令处理器
	 */
	void registerHandler(int symbol, RemoteCommandProcessor handler);
	
	/**
	 * 远程命令处理器选择
	 */
	RemoteCommandProcessor select(int symbol);
	
}
