package io.github.pikaq.remoting.protocol;

import lombok.Data;

@Data
public class HeartBeatRequest implements Packet {

	private long lastTimestap;

}
