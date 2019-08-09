package io.github.pikaq.initialization;

import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.initialization.support.Initable;
import io.github.pikaq.remoting.protocol.command.CommandCode;
import io.github.pikaq.remoting.protocol.command.RemoteCommandFactory;
import io.github.pikaq.remoting.server.PingCommandProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandHandlerRegisterInit implements Initable {

	@Override
	public void init() {
		log.info("CommandHandlerRegisterInit init.");
		
		//注册服务端心跳请求处理器
		RemoteCommandFactory remoteCommandFactory = SingletonFactoy.get(RemoteCommandFactory.class);
		remoteCommandFactory.registerHandler(CommandCode.HEART_BEAT_REQ.getCode(), new PingCommandProcessor());
	}

	@Override
	public int getOrder() {
		return HIGHEST_LEVEL - 1;
	}

}
