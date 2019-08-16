package io.github.pikaq.protocol.command;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import io.github.pikaq.PikaqConst;
import io.github.pikaq.common.exception.RemoteCommandException;
import io.github.pikaq.protocol.RemotingRequestProcessor;

@SuppressWarnings("all")
public class RemoteCommandFactory {

	private static final Logger LOG = LoggerFactory.getLogger(RemoteCommandFactory.class);

	private static final ConcurrentMap<Integer, Class<? extends RemotingCommand>> MAPPINGS = Maps.newConcurrentMap();

	private static final ConcurrentMap<Integer, RemotingRequestProcessor> DISPATCHER = Maps.newConcurrentMap();

	private AtomicBoolean init = new AtomicBoolean(false);

	public RemoteCommandFactory() {
		load();
	}

	private void load() {
		if (!init.compareAndSet(false, true)) {
			return;
		}
		String scannerPath = PikaqConst.COMMAND_SCANNER_PATH;
		Reflections packageInfo = new Reflections(scannerPath);
		Set<Class<? extends RemotingCommand>> subs = packageInfo.getSubTypesOf(RemotingCommand.class);
		if (subs.isEmpty()) {
			throw new RemoteCommandException("加载包错误，未找到命令实现，scannerPath=" + scannerPath);
		}
		for (Class<? extends RemotingCommand> remoteCommandClazz : subs) {
			if (Modifier.isAbstract(remoteCommandClazz.getModifiers())) {
				continue;
			}
			try {
				RemotingCommand remoteCommandInstance = remoteCommandClazz.newInstance();
				int requestCode = remoteCommandInstance.getRequestCode();
				Class<? extends RemotingCommand> prev = MAPPINGS.putIfAbsent(requestCode, remoteCommandClazz);
				if (prev != null) {
					LOG.warn("远程命令[{}]:{} 已初始化过，不再重复加载，请检查扫描包", requestCode,
							remoteCommandInstance.getClass().getCanonicalName());
				} else {
					LOG.info("加载远程命令[{}]:{}", requestCode, remoteCommandInstance.getClass().getCanonicalName());
				}

			} catch (Throwable e) {
				LOG.error("加载包错误，", e);
				throw new RemoteCommandException(e);
			}
		}
	}

	public Class<? extends RemotingCommand> fromRequestCode(int requestCode) {
		Iterator<Entry<Integer, Class<? extends RemotingCommand>>> iterator = MAPPINGS.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, Class<? extends RemotingCommand>> entry = (Entry<Integer, Class<? extends RemotingCommand>>) iterator
					.next();
			if (entry.getKey() == requestCode) {
				return entry.getValue();
			}
		}
		LOG.error(requestCode + " :target requestCode not found");
		throw new RemoteCommandException(requestCode + " :target requestCode not found");
	}

	public RemotingRequestProcessor<RemotingCommand, RemotingCommand> select(int requestCode) {
		return DISPATCHER.get(requestCode);
	}

	public void registerHandler(int requestCode, RemotingRequestProcessor handler) {
		RemotingRequestProcessor prev = DISPATCHER.putIfAbsent(requestCode, handler);
		if(prev != null){
			LOG.warn("requestCode has already register Handler . ignore ");
		}
	}

}
