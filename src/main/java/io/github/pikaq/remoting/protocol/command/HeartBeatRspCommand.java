package io.github.pikaq.remoting.protocol.command;

import io.github.pikaq.remoting.protocol.command.CommandCode;
import io.github.pikaq.remoting.protocol.command.CommandCodeType;
import io.github.pikaq.remoting.protocol.command.RemoteResponseCommand;

/**
 * 心跳响应命令
 * @author pleuvoir
 *
 */
public class HeartBeatRspCommand extends RemoteResponseCommand {

	@Override
	public CommandCode getCommandCode() {
		return CommandCode.HEART_BEAT_RSP;
	}

	@Override
	public CommandCodeType commandCodeType() {
		return CommandCodeType.SYSTEM;
	}

}
