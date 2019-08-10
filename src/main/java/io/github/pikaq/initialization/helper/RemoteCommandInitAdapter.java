package io.github.pikaq.initialization.helper;

import org.apache.commons.lang3.StringUtils;

import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.initialization.RemoteCommandInit;
import io.github.pikaq.initialization.support.Initable;
import io.github.pikaq.remoting.protocol.command.RemoteCommandFactory;

public abstract class RemoteCommandInitAdapter implements Initable {

	@Override
	public int getOrder() {
		return RemoteCommandInit.ORDER;
	}

	@Override
	public void init() {
		String location = location();
		if (StringUtils.isBlank(location)) {
			return;
		}
		RemoteCommandFactory remoteCommandFactory = SingletonFactoy.get(RemoteCommandFactory.class);
		// 扫描用户提供的远程命令
		remoteCommandFactory.load(location);
	}

	/**
	 * 远程命令所在的包
	 */
	protected abstract String location();
}
