package io.github.pikaq.remoting.protocol.builder;

import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Maps;

import io.github.pikaq.remoting.protocol.Packet;
import io.github.pikaq.remoting.protocol.RemoteCommand.Command;
import io.github.pikaq.remoting.protocol.impl.HeartBeatRequest;

public abstract class AbstractPacketBuilder implements PacketBuilder {

	static final ConcurrentMap<Command, Class<? extends Packet>> MAPPINGS = Maps.newConcurrentMap();

	static {
		MAPPINGS.put(Command.HEART_BEAT_REQ, HeartBeatRequest.class);
		MAPPINGS.put(Command.HEART_BEAT_RSP, HeartBeatRequest.class);
	}

	public static Class<? extends Packet> getPacketClazz(Command cmd) {
		return MAPPINGS.get(cmd);
	}

	public static void register(Command cmd, Class<? extends Packet> packetClazz) {
		MAPPINGS.putIfAbsent(cmd, packetClazz);
	}

	@Override
	public Class<? extends Packet> get(Command cmd) {
		return MAPPINGS.get(cmd);
	}

}
