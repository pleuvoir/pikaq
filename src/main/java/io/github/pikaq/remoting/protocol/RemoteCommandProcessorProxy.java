package io.github.pikaq.remoting.protocol;

import io.github.pikaq.remoting.protocol.command.RemoteCommand;

public interface RemoteCommandProcessorProxy {

	void deBeforeRequest(final RemoteCommand request);

	void deAfterRequest(final RemoteCommand request);

}
