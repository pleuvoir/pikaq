package io.github.pikaq.remoting;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import io.github.pikaq.remoting.protocol.command.RemoteCommand;

/**
 * 调度器
 * 
 * @author pleuvoir
 *
 */
public class Pendings {

	private static ConcurrentHashMap<String, CompletableFuture<RemoteCommand>> REQUEST = new ConcurrentHashMap<>();

	public static void put(String id, CompletableFuture<RemoteCommand> promise) {
		REQUEST.put(id, promise);
	}

	public static CompletableFuture<RemoteCommand> remove(String id) {
		return REQUEST.remove(id);
	}
}
