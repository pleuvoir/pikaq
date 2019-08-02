package io.github.pikaq.remoting.protocol;

import lombok.Data;

@Data
public class HeartBeatResponse implements Packet {

	private long lastTimestap;
}
