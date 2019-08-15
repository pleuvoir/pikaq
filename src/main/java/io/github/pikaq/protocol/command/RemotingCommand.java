package io.github.pikaq.protocol.command;

import io.github.pikaq.common.util.ToJSON;
import io.github.pikaq.protocol.RemotingCommandType;
import lombok.Data;

/**
 * 远程命令
 * 
 * @author pleuvoir
 *
 */
@Data
public class RemotingCommand implements ToJSON {

	// 请求唯一id，client和server共享
	protected String messageId;

	// 指令，用于匹配远程命令
	protected int requestCode;

	protected RemotingCommandType commandType;

	// 是否需要响应
	protected boolean responsible;

}
