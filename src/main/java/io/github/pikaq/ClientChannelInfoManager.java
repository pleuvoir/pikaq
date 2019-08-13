package io.github.pikaq;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientChannelInfoManager {

	private Map<String, ClientChannelInfo> table = new ConcurrentHashMap<>(16);

	public void add(String channelId, ClientChannelInfo info) {
		table.put(channelId, info);
	}

	public ClientChannelInfo get(String channelId) {
		return table.get(channelId);
	}

}
