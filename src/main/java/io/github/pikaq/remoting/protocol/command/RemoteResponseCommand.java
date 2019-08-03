package io.github.pikaq.remoting.protocol.command;

/**
 * 远程响应命令
 * 
 * @author pleuvoir
 *
 */
public abstract class RemoteResponseCommand extends RemoteBaseCommand {

	@Override
	public boolean isRequest() {
		return false;
	}

}
