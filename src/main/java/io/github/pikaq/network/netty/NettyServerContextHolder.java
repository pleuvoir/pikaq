package io.github.pikaq.network.netty;

import io.netty.util.concurrent.FastThreadLocal;

public class NettyServerContextHolder {

	static final FastThreadLocal<NettyServerContext> CONTEXT = new FastThreadLocal<>();

	public static NettyServerContext current() {
		return CONTEXT.get();
	}

	public static void set(NettyServerContext serverContext) {
		CONTEXT.set(serverContext);
	}

	public static void clear() {
		CONTEXT.remove();
	}
}
