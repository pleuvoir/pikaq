package io.github.pikaq.protocol.command;

import com.alibaba.fastjson.JSON;

import io.github.pikaq.MessageFrom;
import io.github.pikaq.common.exception.RemoteCommandException;
import lombok.Data;

/**
 * 远程命令
 * 
 * @author pleuvoir
 *
 */
@Data
public class RemotingCommand implements Cloneable {

	// 请求唯一id，client和server共享
	protected String messageId;

	// 指令，用于匹配远程命令
	protected int requestCode;

	protected RemotingCommandType commandType;

	// 是否需要响应
	protected boolean responsible;

	protected MessageFrom messageFrom;

	public void fromServer() {
		setMessageFrom(MessageFrom.SERVER);
	}

	public void fromClient() {
		setMessageFrom(MessageFrom.CLIENT);
	}
	
	public void markRequest() {
		setCommandType(RemotingCommandType.REQUEST_COMMAND);
	}

	public void markResponse() {
		setCommandType(RemotingCommandType.RESPONSE_COMMAND);
	}

	public String toJSON() {
		return JSON.toJSONString(this);
	}

	@Override
	public String toString() {
		return toJSON();
	}
	
	@Override
	public RemotingCommand clone() {
		RemotingCommand cloned = null;
		try {
			cloned = (RemotingCommand) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RemoteCommandException(e);
		}
		return cloned;
	}



}
