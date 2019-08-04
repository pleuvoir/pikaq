package io.github.pikaq.remoting.protocol.command;

import io.github.pikaq.remoting.protocol.RemoteCommandHandler;

/**
 * 远程命令工厂
 * 
 * <p>构建远程命令（通常是发送给对端的数据对象）该类供系统预制指令使用
 * 
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
	Class<? extends RemoteCommand> fromSymbol(int symbol) throws RemoteCommandException;
	
	/**
	 * 远程命令处理器选择
	 */
	RemoteCommandHandler<RemoteCommand, RemoteCommand> select(int symbol);
	
	void registerHandler(RemoteCommand cmd,RemoteCommandHandler handler);
	
}
