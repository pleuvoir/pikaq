package io.github.pikaq.initialization.helper;

import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.initialization.CommandProcessorInit;
import io.github.pikaq.initialization.support.Initable;
import io.github.pikaq.remoting.protocol.RemoteCommandProcessor;
import io.github.pikaq.remoting.protocol.command.RemoteCommandFactory;

public abstract class CommandProcessorInitAdapter implements Initable {

	@Override
	public int getOrder() {
		return CommandProcessorInit.ORDER;
	}

	@SuppressWarnings("rawtypes")
	protected void registerHandler(int symbol, RemoteCommandProcessor handler) {
		RemoteCommandFactory remoteCommandFactory = SingletonFactoy.get(RemoteCommandFactory.class);
		remoteCommandFactory.registerHandler(symbol, handler);
	}

}
