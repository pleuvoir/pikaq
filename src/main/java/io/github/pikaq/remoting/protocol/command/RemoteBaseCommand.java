package io.github.pikaq.remoting.protocol.command;

import java.util.Map;

import com.google.common.collect.Maps;

import io.github.pikaq.common.util.Generator;

public abstract class RemoteBaseCommand extends RemoteCommand {

	protected Map<String, Object> attachments = Maps.newConcurrentMap();

	@Override
	public String getRequestId() {
		return Generator.nextUUID();
	}

	@Override
	public Map<String, Object> getAttachs() {
		return attachments;
	}

	@Override
	public void setAttachs(String key, Object value) {
		attachments.put(key, value);
	}

	public void set(String key, Object value) {
		attachments.put(key, value);
	}
	
	public String getString(String key) {
		return String.valueOf(attachments.get(key));
	}

	public int getInteger(String key) {
		return Integer.valueOf(getString(key));
	}

}
