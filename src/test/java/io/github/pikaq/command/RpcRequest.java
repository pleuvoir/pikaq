package io.github.pikaq.command;

import io.github.pikaq.protocol.command.RemoteBaseCommand;
import io.github.pikaq.protocol.command.RemotingCommandType;

public class RpcRequest extends RemoteBaseCommand{

	@Override
	public boolean responsible() {
		return true;
	}

	@Override
	public int requestCode() {
		return 55;
	}

	@Override
	public RemotingCommandType remotingCommandType() {
		return RemotingCommandType.REQUEST_COMMAND;
	}

}
