package io.github.pikaq.remoting;

import io.github.pikaq.remoting.protocol.HeartBeatRequest;
import io.github.pikaq.remoting.protocol.Packet;

/**
 * 心跳请求命令
 * @author pleuvoir
 *
 */
public class HeartBeatReqCommand extends RemoteRequestCommand<HeartBeatRequest> {

	@Override
	public CommandCode getCommandCode() {
		return CommandCode.HEART_BEAT_REQ;
	}

	@Override
	public CommandCodeType commandCodeType() {
		return CommandCodeType.SYSTEM;
	}
	
	@Override
	public Packet getPacket() {
		HeartBeatRequest heartBeatRequest = new HeartBeatRequest();
		return heartBeatRequest;
	}

}
