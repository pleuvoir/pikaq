package io.github.pikaq.protocol.command;

import io.github.pikaq.common.util.Generator;
import io.github.pikaq.protocol.RemotingCommandType;

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
		requestCode = requestCode();
		responsible = responsible();
	}

	public void markRequest(){
		commandType = RemotingCommandType.REQUEST_COMMAND;
	}
	
	public void markResponse(){
		commandType = RemotingCommandType.RESPONSE_COMMAND;
	}

	/**
	 * 是否需要对端响应
	 */
	public abstract boolean responsible();

	public abstract int requestCode();
}
