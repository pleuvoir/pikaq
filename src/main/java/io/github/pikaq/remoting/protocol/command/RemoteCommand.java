package io.github.pikaq.remoting.protocol.command;

import java.util.Map;

import com.google.common.collect.Maps;

import io.github.pikaq.common.util.ToJSON;
import lombok.Getter;
import lombok.Setter;

/**
 * 远程命令
 * @author pleuvoir
 *
 */
public class RemoteCommand implements ToJSON {
	
	protected RemoteCommand(){}

	//请求唯一id，client和server共享
	@Getter
	@Setter
	protected String id;
	
	//指令，用于匹配远程命令
	@Getter
	@Setter
	protected int symbol;
	
	//命令码类型 SYSTEM, RPC, USER
	@Getter
	@Setter
	protected CommandCodeType commandCodeType;
	
	//附加参数
	@Getter
	@Setter
	protected Map<String, Object> attachments = Maps.newConcurrentMap();

	
	public void set(String key, Object value) {
		attachments.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key){
		return (T) attachments.get(key);
	}
}
