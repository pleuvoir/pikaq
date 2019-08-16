package io.github.pikaq.initialization;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.initialization.support.Initable;
import io.github.pikaq.protocol.CarrierCompositeProcessor;
import io.github.pikaq.protocol.codec.RemoteCommandCodecHandler;
import io.github.pikaq.protocol.command.RemoteCommandFactory;
import io.github.pikaq.protocol.command.RequestCode;
import io.github.pikaq.server.PingRequestProcessor;
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
		
		// 注册编码器
		SingletonFactoy.register(RemoteCommandCodecHandler.class, new RemoteCommandCodecHandler());
		// akka
		SingletonFactoy.register(ActorSystem.class, initActorSystem());

		// 加载远程命令工厂
		RemoteCommandFactory factory = new RemoteCommandFactory();
		SingletonFactoy.register(RemoteCommandFactory.class, factory);
		// 服务端处理心跳
		factory.registerHandler(RequestCode.HEART_BEAT_REQ.getCode(), new PingRequestProcessor());
		// 处理托架命令
		factory.registerHandler(RequestCode.CARRIER.getCode(), new CarrierCompositeProcessor());

	}

	// 初始化ActorSystem
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
