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

import io.github.pikaq.remoting.protocol.RemoteCommandProcessor;

@SuppressWarnings("all")
public class DefaultRemoteCommandFactory implements RemoteCommandFactory {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultRemoteCommandFactory.class);

	private static final ConcurrentMap<Integer, Class<? extends RemoteCommand>> MAPPINGS = Maps.newConcurrentMap();

	private static final ConcurrentMap<Integer, RemoteCommandProcessor> DISPATCHER = Maps.newConcurrentMap();

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
				int symbol = remoteCommandInstance.getSymbol();
				Class<? extends RemoteCommand> prev = MAPPINGS.putIfAbsent(symbol, remoteCommandClazz);
				if (prev != null) {
					LOG.warn("远程命令[{}][{}]:{} 已初始化过，不再重复加载，请检查扫描包", remoteCommandInstance.getCommandCodeType(), symbol,
							remoteCommandInstance.getClass().getCanonicalName());
				} else {
					LOG.info("加载远程命令[{}][{}]:{}", remoteCommandInstance.getCommandCodeType(), symbol,
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
		Class<? extends RemoteCommand> remoteCommandClazz = MAPPINGS.get(code.getCode());
		try {
			RemoteCommand cmd = remoteCommandClazz.newInstance();
			return cmd;
		} catch (InstantiationException | IllegalAccessException e) {
			LOG.error("实例化远程命令失败，", e);
			throw new RemoteCommandException(e);
		}
	}

	@Override
	public Class<? extends RemoteCommand> fromSymbol(int symbol) {
		Iterator<Entry<Integer, Class<? extends RemoteCommand>>> iterator = MAPPINGS.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Integer, java.lang.Class<? extends RemoteCommand>> entry = (Map.Entry<Integer, java.lang.Class<? extends RemoteCommand>>) iterator
					.next();
			if (entry.getKey() == symbol) {
				return entry.getValue();
			}
		}
		LOG.error(symbol + " :target CommandCode not found");
		throw new RemoteCommandException(symbol + " :target CommandCode not found");
	}

	@Override
	public RemoteCommandProcessor<RemoteCommand, RemoteCommand> select(int symbol) {
		return DISPATCHER.get(symbol);
	}

	@Override
	public void registerHandler(int symbol, RemoteCommandProcessor handler) {
		DISPATCHER.putIfAbsent(symbol, handler);
	}

}
