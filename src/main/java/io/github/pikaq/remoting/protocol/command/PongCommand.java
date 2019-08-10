package io.github.pikaq.remoting.protocol.command;

/**
 * 心跳响应命令
 * @author pleuvoir
 *
 */
public class PongCommand extends RemoteBaseCommand {

	@Override
	public int symbol() {
		return CommandCode.HEART_BEAT_RSP.getCode();
	}

	@Override
	public CommandCodeType commandCodeType() {
		return CommandCodeType.SYSTEM;
	}

	@Override
	public boolean responsible() {
		return false;
	}

}
