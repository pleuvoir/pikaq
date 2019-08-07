package io.github.pikaq.remoting.protocol.command;

import io.github.pikaq.common.util.Generator;

/**
 * 所有的远程命令通过继承此类实现
 * @author pleuvoir
 *
 */
public abstract class RemoteBaseCommand extends RemoteCommand {

	public RemoteBaseCommand() {
		super();
		setId(Generator.nextUUID());
		setCommandCodeType(commandCodeType());
		setSymbol(symbol());
	}

	public abstract CommandCodeType commandCodeType();

	public abstract int symbol();
}
