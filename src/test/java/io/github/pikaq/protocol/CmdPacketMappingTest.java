package io.github.pikaq.protocol;

import io.github.pikaq.remoting.protocol.RemoteCommand.Command;
import io.github.pikaq.remoting.protocol.builder.CmdPacketMapping;
import io.github.pikaq.remoting.protocol.impl.HeartBeatRequest;
import io.github.pikaq.remoting.protocol.impl.HeartBeatResponse;

public class CmdPacketMappingTest {

	public static void main(String[] args) {
		Command command = CmdPacketMapping.getCommand(HeartBeatRequest.class);
		System.out.println(command); //HEART_BEAT_REQ

		System.out.println(CmdPacketMapping.getCommand(HeartBeatResponse.class)); //HEART_BEAT_RSP
	}
}
