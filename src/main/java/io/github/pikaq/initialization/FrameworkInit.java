package io.github.pikaq.initialization;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.extension.ExtensionLoader;
import io.github.pikaq.initialization.support.Initable;
import io.github.pikaq.remoting.client.ClientRemoteCommandtDispatcher;
import io.github.pikaq.remoting.client.RemoteCommandLifeCycleListener;
import io.github.pikaq.remoting.protocol.command.DefaultRemoteCommandFactory;
import io.github.pikaq.remoting.protocol.command.RemoteCommandFactory;
import io.github.pikaq.remoting.server.ServerRemoteCommandtDispatcher;
import io.github.pikaq.serialization.Serializer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FrameworkInit implements Initable {

	
	@Override
	public int getOrder() {
		return HIGHEST_LEVEL;
	}

	@Override
	public void init() {
		log.info("FrameworkInit init.");
		
		//加载SPI
		ExtensionLoader.initExtension(Serializer.class);
		
		//注册单例
		
		//客户端生命周期监听器
		SingletonFactoy.register(RemoteCommandLifeCycleListener.class, new RemoteCommandLifeCycleListener.Adapter());
		//远程命令工厂
		SingletonFactoy.register(RemoteCommandFactory.class, new DefaultRemoteCommandFactory());
		//客户端命令分发器
		SingletonFactoy.register(ClientRemoteCommandtDispatcher.class, new ClientRemoteCommandtDispatcher());
		//服务端命令分发器
		SingletonFactoy.register(ServerRemoteCommandtDispatcher.class, new ServerRemoteCommandtDispatcher());
		//akka
		SingletonFactoy.register(ActorSystem.class, initActorSystem());
	}
	
	
	//初始化ActorSystem
	private static ActorSystem initActorSystem() {
		StringBuilder s = new StringBuilder();
		s.append("akka.loggers = [\"akka.event.slf4j.Slf4jLogger\"], ");
		s.append("akka.logging-filter = \"akka.event.slf4j.Slf4jLoggingFilter\", ");
		if (log.isTraceEnabled()) {
			s.append("akka.loglevel = \"TRACE\", ");
		} else if (log.isDebugEnabled()) {
			s.append("akka.loglevel = \"DEBUG\", ");
		} else if (log.isInfoEnabled()) {
			s.append("akka.loglevel = \"INFO\", ");
		} else if (log.isWarnEnabled()) {
			s.append("akka.loglevel = \"WARNING\", ");
		} else if (log.isErrorEnabled()) {
			s.append("akka.loglevel = \"ERROR\", ");
		}
		return ActorSystem.create("system", ConfigFactory.parseString(s.toString()));
	}
	
}
