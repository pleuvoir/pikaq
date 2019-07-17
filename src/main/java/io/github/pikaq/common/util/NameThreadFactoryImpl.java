package io.github.pikaq.common.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class NameThreadFactoryImpl implements ThreadFactory {

	private final String namePrefix;

	private final boolean daemon;

	private AtomicLong count = new AtomicLong();

	public NameThreadFactoryImpl(String namePrefix) {
		this(namePrefix, false);
	}

	public NameThreadFactoryImpl(String namePrefix, boolean daemon) {
		this.namePrefix = namePrefix;
		this.daemon = daemon;
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setDaemon(daemon);
		t.setName(this.namePrefix + "-" + this.count.getAndIncrement());
		return t;
	}
}
