
package io.github.pikaq.command;


import io.github.pikaq.protocol.command.RemoteBaseCommand;

public class UserReqCommand extends RemoteBaseCommand {

	private String name;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int requestCode() {
		return 99;
	}


	@Override
	public boolean responsible() {
		return false;
	}

}
