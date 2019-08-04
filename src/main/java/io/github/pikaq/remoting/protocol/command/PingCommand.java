package io.github.pikaq.remoting.protocol.command;

/**
 * 心跳请求命令
 * @author pleuvoir
 *
 */
public class PingCommand extends RemoteBaseCommand {

	@Override
	public int getSymbol() {
		return CommandCode.HEART_BEAT_REQ.getCode();
	}

	@Override
	public CommandCodeType getCommandCodeType() {
		return CommandCodeType.SYSTEM;
	}

}
