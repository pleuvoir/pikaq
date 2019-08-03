package io.github.pikaq.remoting.protocol.command;

import io.github.pikaq.remoting.protocol.command.CommandCode;
import io.github.pikaq.remoting.protocol.command.CommandCodeType;
import io.github.pikaq.remoting.protocol.command.RemoteRequestCommand;

/**
 * 心跳请求命令
 * @author pleuvoir
 *
 */
public class HeartBeatReqCommand extends RemoteRequestCommand {

	@Override
	public CommandCode getCommandCode() {
		return CommandCode.HEART_BEAT_REQ;
	}

	@Override
	public CommandCodeType commandCodeType() {
		return CommandCodeType.SYSTEM;
	}

}
