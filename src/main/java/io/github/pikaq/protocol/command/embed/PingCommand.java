package io.github.pikaq.protocol.command.embed;

import io.github.pikaq.protocol.command.RemoteBaseCommand;
import io.github.pikaq.protocol.command.RemotingCommandType;
import io.github.pikaq.protocol.command.RequestCode;

/**
 * 心跳请求命令
 * 
 * @author pleuvoir
 *
 */
public class PingCommand extends RemoteBaseCommand {

	@Override
	public int requestCode() {
		return RequestCode.HEART_BEAT_REQ.getCode();
	}

	@Override
	public boolean responsible() {
		return true;
	}

	@Override
	public RemotingCommandType remotingCommandType() {
		return RemotingCommandType.REQUEST_COMMAND;
	}

}
