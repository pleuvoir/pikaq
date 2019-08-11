package io.github.pikaq.remoting.protocol.command;

import io.github.pikaq.common.util.Generator;

/**
 * 所有的远程命令通过继承此类实现
 * 
 * @author pleuvoir
 *
 */
public abstract class RemoteBaseCommand extends RemotingCommand {

	public RemoteBaseCommand() {
		super();
		messageId = Generator.nextUUID();
		commandCodeType = commandCodeType();
		symbol = symbol();
		responsible = responsible();
	}

	public abstract CommandCodeType commandCodeType();

	/**
	 * 是否需要对端响应
	 */
	public abstract boolean responsible();

	public abstract int symbol();
}
