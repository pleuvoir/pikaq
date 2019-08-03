package io.github.pikaq.remoting.protocol.command;

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

public class DefaultRemoteCommandFactory implements RemoteCommandFactory {

	public static final DefaultRemoteCommandFactory INSTANCE = new DefaultRemoteCommandFactory();

	private static final ConcurrentMap<CommandCode, Class<? extends RemoteCommand>> MAPPINGS = Maps.newConcurrentMap();

	@Override
	public void load(String scannerPath) {
		Reflections packageInfo = new Reflections(scannerPath);
		Set<Class<? extends RemoteCommand>> subs = packageInfo.getSubTypesOf(RemoteCommand.class);
		if (subs.isEmpty()) {
			throw new RemoteCommandException("加载包错误，未找到命令实现，scannerPath=" + scannerPath);
		}
		for (Class<? extends RemoteCommand> remoteCommandClazz : subs) {
			if (Modifier.isAbstract(remoteCommandClazz.getModifiers())) {
				continue;
			}
			try {
				RemoteCommand remoteCommandInstance = remoteCommandClazz.newInstance();
				CommandCode commandCode = remoteCommandInstance.getCommandCode();
			
				Class<? extends RemoteCommand> prev = MAPPINGS.putIfAbsent(commandCode, remoteCommandClazz);
				if (prev != null) {
					LOG.warn("远程命令[{}][{}]:{} 已初始化过，不再重复加载，请检查扫描包", remoteCommandInstance.commandCodeType(), commandCode,
							remoteCommandInstance.getClass().getCanonicalName());
				} else {
					LOG.info("加载远程命令[{}][{}]:{}", remoteCommandInstance.commandCodeType(), commandCode,
							remoteCommandInstance.getClass().getCanonicalName());
				}
				
			} catch (Throwable e) {
				LOG.error("加载包错误，", e);
				throw new RemoteCommandException(e);
			}
		}
	}

	@Override
	public RemoteCommand newRemoteCommand(CommandCode code) {
		Class<? extends RemoteCommand> remoteCommandClazz = MAPPINGS.get(code);
		try {
			RemoteCommand cmd = remoteCommandClazz.newInstance();
			return cmd;
		} catch (InstantiationException | IllegalAccessException e) {
			LOG.error("实例化远程命令失败，", e);
			throw new RemoteCommandException(e);
		}
	}

	@Override
	public Class<? extends RemoteCommand> fromCommandCode(CommandCode code) {
		Iterator<Entry<CommandCode, Class<? extends RemoteCommand>>> iterator = MAPPINGS.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<CommandCode, java.lang.Class<? extends RemoteCommand>> entry = (Map.Entry<CommandCode, java.lang.Class<? extends RemoteCommand>>) iterator
					.next();
			if (entry.getKey() == code) {
				return entry.getValue();
			}
		}
		LOG.error(code + " :target CommandCode not found");
		throw new RemoteCommandException(code + " :target CommandCode not found");
	}

	@Override
	public RemoteCommand convertConvert2Response(CommandCode code) {
		if (code.isRequest() && code.toResponse() != null) {
			return newRemoteCommand(code.toResponse());
		}
		throw new RemoteCommandException(code + ":无对应的响应命令");
	}

	@Override
	public RemoteCommand convertConvert2Response(RemoteCommand request) throws RemoteCommandException {
		return this.convertConvert2Response(request.getCommandCode());
	}

	private DefaultRemoteCommandFactory() {
	}

	private static final Logger LOG = LoggerFactory.getLogger(DefaultRemoteCommandFactory.class);
}
