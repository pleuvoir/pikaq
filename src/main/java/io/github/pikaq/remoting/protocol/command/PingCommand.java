package io.github.pikaq.remoting.protocol.command;

/**
 * 心跳请求命令
 * 
 * @author pleuvoir
 *
 */
public class PingCommand extends RemoteBaseCommand {

	private String clientID;

	@Override
	public int requestCode() {
		return RequestCode.HEART_BEAT_REQ.getCode();
	}

	@Override
	public boolean responsible() {
		return true;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

}
