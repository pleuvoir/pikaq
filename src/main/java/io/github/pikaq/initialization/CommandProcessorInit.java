package io.github.pikaq.initialization;

import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.initialization.support.Initable;
import io.github.pikaq.remoting.protocol.command.CommandCode;
import io.github.pikaq.remoting.protocol.command.RemoteCommandFactory;
import io.github.pikaq.remoting.server.PingCommandProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandProcessorInit implements Initable {

	public static final int ORDER = HIGHEST_LEVEL - 1;

	@Override
	public void init() {
		log.info("CommandHandlerRegisterInit init.");
		RemoteCommandFactory remoteCommandFactory = SingletonFactoy.get(RemoteCommandFactory.class);
		// 注册服务端心跳请求处理器
		remoteCommandFactory.registerHandler(CommandCode.HEART_BEAT_REQ.getCode(), new PingCommandProcessor());
	}

	@Override
	public int getOrder() {
		return ORDER;
	}

}
