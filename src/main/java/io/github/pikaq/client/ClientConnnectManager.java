package io.github.pikaq.client;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.MoreExecutors;

import io.github.pikaq.common.exception.RemotingSendRequestException;
import io.github.pikaq.common.util.NameThreadFactoryImpl;
import io.github.pikaq.common.util.RemotingUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * 连接管理器
 * 
 * @author pleuvoir
 *
 */
public class ClientConnnectManager {

	private final Bootstrap bootstrap;
	
	private ScheduledExecutorService holdExcutor;

	public ClientConnnectManager(Bootstrap bootstrap) {
		this.bootstrap = bootstrap;
		holdExcutor = Executors.newSingleThreadScheduledExecutor((new NameThreadFactoryImpl("client_connnect_manager", true)));
	}

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
		holdExcutor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				tables.forEach((k, v) -> {
					if (!validate(v)) {
						LOG.debug("剔除连接通道：{}", v.localAddress());
						removeChannel(k);
					}
				});
			}
		}, 5, 30, TimeUnit.SECONDS);
	}

	public void printAliveChannel() {
		Executors.newSingleThreadScheduledExecutor((new NameThreadFactoryImpl("hold_conn")))
				.scheduleAtFixedRate(new Runnable() {
					@Override
					public void run() {
						tables.forEach((k, v) -> {
							LOG.debug("目前存活的通道：{}", v.localAddress());
						});
					}
				}, 60, 300, TimeUnit.SECONDS);
	}

	public synchronized void removeChannel(String addr) {
		tables.remove(addr);
	}

	public synchronized boolean validate(Channel channel) {
		return channel != null && channel.isActive();
	}

	public synchronized Channel getOrCreateChannel(String addr) throws RemotingSendRequestException {
		Channel channel = tables.get(addr);
		if (channel == null) {
			Channel createNewChannel = createNewChannel(addr);
			
			if (!validate(createNewChannel)) {
				throw new RemotingSendRequestException("can't connect fail, addr=" + addr);
			}
			
			this.tables.put(addr, createNewChannel);
			return createNewChannel;
		}

		if (!validate(channel)) {
			channel.close();
			removeChannel(RemotingUtils.parseChannelRemoteAddr(channel));
		}
		return channel;
	}

	private Channel createNewChannel(String addr) {
		ChannelFuture future = this.bootstrap.connect(RemotingUtils.string2SocketAddress(addr));
		future.awaitUninterruptibly();
		Channel newChannel = future.channel();
		return newChannel;
	}
	
	public synchronized void release() {
		tables.clear();
		tables = null;
		if (holdExcutor != null) {
			MoreExecutors.shutdownAndAwaitTermination(holdExcutor, 8, TimeUnit.SECONDS);
		}
	}

	protected static final Logger LOG = LoggerFactory.getLogger(ClientConnnectManager.class);
}
