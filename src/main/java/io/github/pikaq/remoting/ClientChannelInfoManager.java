package io.github.pikaq.remoting;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientChannelInfoManager {

	private Map<String, ClientChannelInfo> table = new ConcurrentHashMap<>(16);

	public void add(String clientId, ClientChannelInfo info) {
		table.put(clientId, info);
	}

	public ClientChannelInfo get(String clientId) {
		return table.get(clientId);
	}

}
