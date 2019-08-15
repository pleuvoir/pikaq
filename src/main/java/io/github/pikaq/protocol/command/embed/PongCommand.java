package io.github.pikaq.protocol.command.embed;

import io.github.pikaq.protocol.command.RemoteBaseCommand;
import io.github.pikaq.protocol.command.RemotingCommandType;
import io.github.pikaq.protocol.command.RequestCode;

/**
 * 心跳响应命令
 * @author pleuvoir
 *
 */
public class PongCommand extends RemoteBaseCommand {

	@Override
	public int requestCode() {
		return RequestCode.HEART_BEAT_RSP.getCode();
	}

	@Override
	public boolean responsible() {
		return false;
	}

	@Override
	public RemotingCommandType remotingCommandType() {
		return RemotingCommandType.RESPONSE_COMMAND;
	}

}
