package io.github.pikaq.remoting.client;

import io.github.pikaq.remoting.RemotingContext;
import io.github.pikaq.remoting.protocol.command.PingCommand;

/**
 * 长连接客户端
 * 
 * @author pleuvoir
 *
 */
public class KeepAliveClient extends AbstractClient {

	private String name;

	public KeepAliveClient(String name) {
		this.name = name;
	}

	@Override
	public String getClientName() {
		return name;
	}

	//连接上就发送一条报备信息 TODO 这个client必须是可推到出确定唯一的
	@Override
	protected void doStart(RemotingContext remotingContext) {
		PingCommand request = new PingCommand();
		remotingContext.getChannel().writeAndFlush(request);
	}

}
