package io.github.pikaq.protocol;

import org.junit.Assert;
import org.junit.Test;

import io.github.pikaq.remoting.protocol.Packet;
import io.github.pikaq.remoting.protocol.RemoteCommand;
import io.github.pikaq.remoting.protocol.RemoteCommand.Command;
import io.github.pikaq.remoting.protocol.builder.DefaultPacketBuilder;
import io.github.pikaq.remoting.protocol.impl.HeartBeatRequest;
import io.github.pikaq.remoting.protocol.impl.HeartBeatResponse;

public class PacketBuilderTest {

	@Test
	public void test() {

		DefaultPacketBuilder builder = new DefaultPacketBuilder();

		RemoteCommand remoteCommand = new RemoteCommand();

		Command req = Command.HEART_BEAT_REQ;
		remoteCommand.setCmd(req);

		Packet reqPacket = builder.build(remoteCommand);

		Assert.assertTrue(reqPacket instanceof HeartBeatRequest);

		Command rsp = req.toResponse();
		remoteCommand.setCmd(rsp);
		Packet rspPacket = builder.build(remoteCommand);

		Assert.assertTrue(rspPacket instanceof HeartBeatResponse);
	}
}
