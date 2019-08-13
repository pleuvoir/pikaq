package io.github.pikaq;

import io.github.pikaq.protocol.command.RemotingCommand;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 调度器
 * 
 * @author pleuvoir
 *
 */
public class Pendings {

	private static ConcurrentHashMap<String, CompletableFuture<RemotingCommand>> REQUEST = new ConcurrentHashMap<>();

	public static void put(String id, CompletableFuture<RemotingCommand> promise) {
		REQUEST.put(id, promise);
	}

	public static CompletableFuture<RemotingCommand> remove(String id) {
		return REQUEST.remove(id);
	}
}
