package io.github.pikaq.remoting;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import io.github.pikaq.remoting.protocol.Packet;

@SuppressWarnings("all")
public class RemoteCommandFactory {
	
	private static final Logger LOG = LoggerFactory.getLogger(RemoteCommandFactory.class);
	
	static final ConcurrentMap<CommandCode, Class<? extends RemoteCommand>> MAPPINGS = Maps.newConcurrentMap();
	
	static {
		Reflections packageInfo = new Reflections("io.github.pikaq.remoting");
		Set<Class<? extends RemoteCommand>> subs = packageInfo.getSubTypesOf(RemoteCommand.class);
		for (Class<? extends RemoteCommand> remoteCommandClazz : subs) {
			if (Modifier.isAbstract(remoteCommandClazz.getModifiers())) {
				continue;
			}
			try {
				RemoteCommand remoteCommandInstance = remoteCommandClazz.newInstance();
				
				LOG.info("加载远程命令[{}]:{}", remoteCommandInstance.getCommandCode(),
						remoteCommandInstance.getClass().getCanonicalName());
				
				MAPPINGS.put(remoteCommandInstance.getCommandCode(), remoteCommandInstance.getClass());
			} catch (Throwable e) {
				LOG.error("加载包错误，", e);
				System.exit(-1);
			}
		}
	}

	
	public static RemoteCommand select(CommandCode commandCode) {
		Class<? extends RemoteCommand> remoteCommand = MAPPINGS.get(commandCode);
		if (remoteCommand == null) {
			LOG.error(commandCode.getCode() + " not found");
			throw new RemoteCommandException(commandCode.getCode() + " not found");
		}
		try {
			return remoteCommand.newInstance();
		} catch (InstantiationException | IllegalAccessException ignore) {
			LOG.error(commandCode.getCode() + " newInstance error");
			throw new RemoteCommandException(commandCode.getCode() + " newInstance error");
		}
	}
	
	public static CommandCode getCommand(Class<? extends Packet> packetClazz) {
		Iterator<Entry<CommandCode, Class<? extends RemoteCommand>>> iterator = MAPPINGS.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<CommandCode, java.lang.Class<? extends RemoteCommand>> entry = (Map.Entry<CommandCode, java.lang.Class<? extends RemoteCommand>>) iterator
					.next();
			RemoteCommand remoteCommand;
			try {
				remoteCommand = entry.getValue().newInstance();
				if (packetClazz == remoteCommand.getPacketClazz()) {
					return entry.getKey();
				}
			} catch (InstantiationException | IllegalAccessException e) {
				LOG.error(packetClazz.getSimpleName() + " newInstance error");
				throw new RemoteCommandException(packetClazz.getSimpleName() + " newInstance error");
			}
		}
		LOG.error(packetClazz.getSimpleName() + " target CommandCode not found");
		throw new RemoteCommandException(packetClazz.getSimpleName() + " target CommandCode not found");
	}
	
}
