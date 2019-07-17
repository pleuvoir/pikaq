package io.github.pikaq.network.server;

import io.netty.util.concurrent.FastThreadLocal;

public class NetworkServerContextHolder {

	static final FastThreadLocal<NetworkServerContext> CONTEXT = new FastThreadLocal<>();

	public static NetworkServerContext current() {
		return CONTEXT.get();
	}

	public static void set(NetworkServerContext serverContext) {
		CONTEXT.set(serverContext);
	}

	public static void clear() {
		CONTEXT.remove();
	}
}
