package io.github.pikaq.remoting.protocol.command;

import java.util.Map;

import com.google.common.collect.Maps;

import io.github.pikaq.common.util.ToJSON;
import io.github.pikaq.remoting.protocol.RemotingCommandType;
import lombok.Getter;
import lombok.Setter;

/**
 * 远程命令
 * @author pleuvoir
 *
 */
public class RemotingCommand implements ToJSON {
	
	protected RemotingCommand(){}

	//请求唯一id，client和server共享
	@Getter
	@Setter
	protected String messageId;
	
	//指令，用于匹配远程命令
	@Getter
	@Setter
	protected int requestCode;
	
	@Getter
	@Setter
	protected RemotingCommandType commandType;
	
	
	//是否需要响应
	@Getter
	@Setter
	protected boolean responsible;
	
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
