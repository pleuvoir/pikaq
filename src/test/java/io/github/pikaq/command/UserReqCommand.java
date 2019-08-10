
package io.github.pikaq.command;

import io.github.pikaq.remoting.protocol.command.CommandCodeType;
import io.github.pikaq.remoting.protocol.command.RemoteBaseCommand;

public class UserReqCommand extends RemoteBaseCommand {

	private String name;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int symbol() {
		return 99;
	}

	@Override
	public CommandCodeType commandCodeType() {
		return CommandCodeType.USER;
	}

	@Override
	public boolean responsible() {
		return false;
	}

}
