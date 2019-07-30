package io.github.pikaq.remoting.protocol.builder;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Maps;

import io.github.pikaq.remoting.protocol.Packet;
import io.github.pikaq.remoting.protocol.RemoteCommand;
import io.github.pikaq.remoting.protocol.RemoteCommand.Command;
import io.github.pikaq.remoting.protocol.impl.HeartBeatRequest;
import io.github.pikaq.remoting.protocol.impl.HeartBeatResponse;

public class CmdPacketMapping {

	static final ConcurrentMap<Command, Class<? extends Packet>> MAPPINGS = Maps.newConcurrentMap();

	static {
		MAPPINGS.putIfAbsent(Command.HEART_BEAT_REQ, HeartBeatRequest.class);
		MAPPINGS.putIfAbsent(Command.HEART_BEAT_RSP, HeartBeatResponse.class);
	}

	public static Class<? extends Packet> getPacketClazz(Command cmd) {
		return MAPPINGS.get(cmd);
	}

	public static Command getCommand(Class<? extends Packet> packetClazz) {
		Iterator<Entry<Command, Class<? extends Packet>>> iterator = MAPPINGS.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<RemoteCommand.Command, java.lang.Class<? extends Packet>> entry = 
					(Map.Entry<RemoteCommand.Command, java.lang.Class<? extends Packet>>) iterator
					.next();
			Class<? extends Packet> value = entry.getValue();
			if(packetClazz == value){
				return entry.getKey();
			}
		}
		return null;
	}

	public static Class<? extends Packet> register(Command cmd, Class<? extends Packet> packetClazz) {
		return MAPPINGS.putIfAbsent(cmd, packetClazz);
	}

}
