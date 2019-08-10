package io.github.pikaq.initialization.helper;

import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.initialization.CommandHandlerInit;
import io.github.pikaq.initialization.support.Initable;
import io.github.pikaq.remoting.protocol.RemoteCommandProcessor;
import io.github.pikaq.remoting.protocol.command.RemoteCommandFactory;

public abstract class CommandHandlerInitAdapter implements Initable {

	@Override
	public int getOrder() {
		return CommandHandlerInit.ORDER;
	}

	@SuppressWarnings("rawtypes")
	protected void registerHandler(int symbol, RemoteCommandProcessor handler) {
		RemoteCommandFactory remoteCommandFactory = SingletonFactoy.get(RemoteCommandFactory.class);
		remoteCommandFactory.registerHandler(symbol, handler);
	}

}
