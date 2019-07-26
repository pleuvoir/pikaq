package io.github.pikaq.remoting.client;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.pikaq.common.util.NameThreadFactoryImpl;
import io.github.pikaq.common.util.RemotingUtils;
import io.netty.channel.Channel;

/**
 * 连接管理器
 * @author pleuvoir
 *
 */
public class ConnnectManager {

	
	protected static final Logger LOG = LoggerFactory.getLogger(ConnnectManager.class);
	
	public static final ConnnectManager INSTANCE = new ConnnectManager();
	
	public ConcurrentHashMap<String, Channel> tables = new ConcurrentHashMap<String, Channel>();
	
	
	public synchronized void putChannel(Channel channel) {
		String addr = RemotingUtils.parseChannelRemoteAddr(channel);
		if (!validate(channel)) {
			return;
		}
		Channel prev = tables.putIfAbsent(addr, channel);
		if (prev != null && validate(prev)) {
			return;
		}
		tables.put(addr, channel);
	}
	
	public void fireHoldTask() {
		Executors.newSingleThreadScheduledExecutor((new NameThreadFactoryImpl("hold_conn")))
				.scheduleAtFixedRate(new Runnable() {
					@Override
					public void run() {
						tables.forEach((k, v) -> {
							if (!validate(v)) {
								removeChannel(k);
							}
						});
					}
				}, 0, 5, TimeUnit.SECONDS);
	}
	
	public void printAliveChannel() {
		Executors.newSingleThreadScheduledExecutor((new NameThreadFactoryImpl("hold_conn")))
				.scheduleAtFixedRate(new Runnable() {
					@Override
					public void run() {
						tables.forEach((k, v) -> {
							LOG.info("目前存活的通道：{}", v.localAddress());
						});
					}
				}, 5, 15, TimeUnit.SECONDS);
	}

	public void getOrCreateChannel(String addr){
		
	}
	
	public synchronized void removeChannel(String addr){
		tables.remove(addr);
	}
	
	public boolean validate(Channel channel) {
		return channel != null && channel.isActive();
	}
	
	private ConnnectManager(){}
}
