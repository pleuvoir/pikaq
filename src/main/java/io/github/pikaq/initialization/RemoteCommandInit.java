package io.github.pikaq.initialization;

import io.github.pikaq.PikaqConst;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.initialization.support.Initable;
import io.github.pikaq.remoting.protocol.command.RemoteCommandFactory;

public class RemoteCommandInit implements Initable {

	public static final int ORDER = HIGHEST_LEVEL - 2;

	@Override
	public int getOrder() {
		return ORDER;
	}

	@Override
	public void init() {
		RemoteCommandFactory remoteCommandFactory = SingletonFactoy.get(RemoteCommandFactory.class);
		// 扫描加载系统预制远程命令
		remoteCommandFactory.load(PikaqConst.COMMAND_SCANNER_PATH);
	}

}
