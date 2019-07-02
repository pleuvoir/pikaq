package io.github.pikaq;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 请求上下文<br>
 * @author pleuvoir
 *
 */
public class InvokeContext {

	public static final String CLIENT_LOCAL_IP 			= 	"pikaq.client.local.ip";
	public static final String CLIENT_LOCAL_PORT 		= 	"pikaq.client.local.port";
	public static final String CLIENT_REMOTE_IP 		= 	"pikaq.client.remote.ip";
	public static final String CLIENT_REMOTE_PORT		= 	"pikaq.client.remote.port";

	public static final String SERVER_LOCAL_IP 			= 	"pikaq.server.local.ip";
	public static final String SERVER_LOCAL_PORT 		= 	"pikaq.server.local.port";
	public static final String SERVER_REMOTE_IP 		= 	"pikaq.server.remote.ip";
	public static final String SERVER_REMOTE_PORT		= 	"pikaq.server.remote.port";

	private Map<String, Object> attachments;

	public InvokeContext() {
		attachments = new ConcurrentHashMap<>(8);
	}

	public void put(String key, Object value) {
		this.attachments.put(key, value);
	}

	public void putIfAbsent(String key, Object value) {
		this.attachments.putIfAbsent(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) this.attachments.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T getOrDefault(String key, T defaultValue) {
		return this.attachments.get(key) != null ? (T) this.attachments.get(key) : defaultValue;
	}

	public void clear() {
		this.attachments.clear();
	}
}
