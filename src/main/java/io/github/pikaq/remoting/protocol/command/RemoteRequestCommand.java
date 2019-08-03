package io.github.pikaq.remoting.protocol.command;

/**
 * 远程请求命令
 * @author pleuvoir
 *
 */
public abstract class RemoteRequestCommand extends RemoteBaseCommand {

	@Override
	public boolean isRequest() {
		return true;
	}

}
