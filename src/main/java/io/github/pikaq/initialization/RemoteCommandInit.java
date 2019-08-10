package io.github.pikaq.initialization;

import io.github.pikaq.PikaqConst;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.initialization.support.Initable;
import io.github.pikaq.remoting.protocol.command.RemoteCommandFactory;

public class RemoteCommandInit implements Initable {

	private RemoteCommandFactory remoteCommandFactory;

	@Override
	public int getOrder() {
		return HIGHEST_LEVEL - 2;
	}

	@Override
	public void init() {
		remoteCommandFactory = SingletonFactoy.get(RemoteCommandFactory.class);
		// 扫描加载系统预制远程命令
		remoteCommandFactory.load(PikaqConst.COMMAND_SCANNER_PATH);
	}

}
