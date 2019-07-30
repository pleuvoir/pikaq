package io.github.pikaq.remoting.protocol.builder;

import io.github.pikaq.remoting.protocol.Packet;
import io.github.pikaq.remoting.protocol.RemoteCommand;

public interface PacketBuilder {

	Packet build(RemoteCommand remoteCommand);

}
