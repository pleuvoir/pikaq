package io.github.pikaq.remoting.protocol.command;

/**
 * 心跳请求命令
 * @author pleuvoir
 *
 */
public class PingCommand extends RemoteBaseCommand {

	@Override
	public int requestCode() {
		return CommandCode.HEART_BEAT_REQ.getCode();
	}

	@Override
	public boolean responsible() {
		return true;
	}

}
