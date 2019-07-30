package io.github.pikaq.remoting.protocol.impl;

import io.github.pikaq.remoting.protocol.RequestPacket;

public class HeartBeatRequest implements RequestPacket {

	public static final HeartBeatRequest INSTANCE = new HeartBeatRequest();
}
