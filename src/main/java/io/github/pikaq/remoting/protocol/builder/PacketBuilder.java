package io.github.pikaq.remoting.protocol.builder;

import io.github.pikaq.remoting.protocol.Packet;
import io.github.pikaq.remoting.protocol.RemoteCommand;
import io.github.pikaq.remoting.protocol.RemoteCommand.Command;

public interface PacketBuilder {

	Packet build(RemoteCommand remoteCommand);

	Class<? extends Packet> get(Command cmd);

}
