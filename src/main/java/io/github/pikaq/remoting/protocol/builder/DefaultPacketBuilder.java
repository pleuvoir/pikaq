package io.github.pikaq.remoting.protocol.builder;

import io.github.pikaq.remoting.protocol.Packet;
import io.github.pikaq.remoting.protocol.RemoteCommand;
import io.github.pikaq.remoting.protocol.RemoteCommand.Command;
import io.github.pikaq.remoting.protocol.impl.HeartBeatRequest;
import io.github.pikaq.remoting.protocol.impl.HeartBeatResponse;

public class DefaultPacketBuilder implements PacketBuilder {

	@Override
	public Packet build(RemoteCommand remoteCommand) {
		Command cmd = remoteCommand.getCmd();

		switch (cmd) {
		case HEART_BEAT_REQ:
			return HeartBeatRequest.INSTANCE;
		case HEART_BEAT_RSP:
			return HeartBeatResponse.INSTANCE;
		default:
			return null;
		}
	}

	public static PacketBuilder getInstance() {
		return INSTANCE;
	}

	private static final DefaultPacketBuilder INSTANCE = new DefaultPacketBuilder();
}
