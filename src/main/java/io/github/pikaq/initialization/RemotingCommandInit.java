package io.github.pikaq.initialization;


import io.github.pikaq.PikaqConst;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.initialization.support.Initable;
import io.github.pikaq.protocol.command.RemoteCommandFactory;

public class RemotingCommandInit implements Initable {

	public static final int ORDER = HIGHEST_LEVEL - 2;

	@Override
	public int getOrder() {
		return ORDER;
	}

	@Override
	public void init() {
		RemoteCommandFactory factory = SingletonFactoy.get(RemoteCommandFactory.class);
		// 加载所有jar中远程命令
		factory.load(PikaqConst.ALL_SCANNER_PATH);
	}

}
