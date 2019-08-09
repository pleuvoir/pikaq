package io.github.pikaq.initialization;

import io.github.pikaq.PikaqConst;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.extension.ExtensionLoader;
import io.github.pikaq.initialization.support.Initable;
import io.github.pikaq.remoting.RemoteCommandLifeCycleListener;
import io.github.pikaq.remoting.protocol.command.DefaultRemoteCommandFactory;
import io.github.pikaq.remoting.protocol.command.RemoteCommandFactory;
import io.github.pikaq.serialization.Serializer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FrameworkInit implements Initable {

	@Override
	public void init() {
		log.info("FrameworkInit init.");
		//加载SPI
		ExtensionLoader.initExtension(Serializer.class);
		//注册单例
		SingletonFactoy.register(RemoteCommandLifeCycleListener.class, new RemoteCommandLifeCycleListener.Adapter());
		SingletonFactoy.register(RemoteCommandFactory.class, new DefaultRemoteCommandFactory());
		//加载远程命令工厂
		SingletonFactoy.get(RemoteCommandFactory.class).load(PikaqConst.COMMAND_SCANNER_PATH);
	}

	@Override
	public int getOrder() {
		return HIGHEST_LEVEL;
	}


}
