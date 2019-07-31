package io.github.pikaq.remoting;

import io.github.pikaq.remoting.protocol.HeartBeatResponse;
import io.github.pikaq.remoting.protocol.Packet;

/**
 * 心跳响应命令
 * @author pleuvoir
 *
 */
public class HeartBeatRspCommand extends RemoteResponseCommand<HeartBeatResponse> {

	@Override
	public CommandCode getCommandCode() {
		return CommandCode.HEART_BEAT_RSP;
	}

	@Override
	public Packet getPacket() {
		HeartBeatResponse heartBeatResponse = new HeartBeatResponse();
		return heartBeatResponse;
	}

	@Override
	public CommandCodeType commandCodeType() {
		return CommandCodeType.SYSTEM;
	}

}
