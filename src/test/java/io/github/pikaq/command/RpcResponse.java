package io.github.pikaq.command;

import io.github.pikaq.protocol.command.RemoteBaseCommand;
import io.github.pikaq.protocol.command.RemotingCommandType;
import lombok.Getter;
import lombok.Setter;

public class RpcResponse extends RemoteBaseCommand {

	@Getter
	@Setter
	private String payload;
	
	@Override
	public boolean responsible() {
		return false;
	}

	@Override
	public int requestCode() {
		return -55;
	}

	@Override
	public RemotingCommandType remotingCommandType() {
		return RemotingCommandType.RESPONSE_COMMAND;
	}

}
