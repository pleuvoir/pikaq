package io.github.pikaq.protocol.command.embed;

import io.github.pikaq.protocol.command.RemoteBaseCommand;
import io.github.pikaq.protocol.command.RemotingCommandType;
import io.github.pikaq.protocol.command.RequestCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CarrierCommand extends RemoteBaseCommand {

	private static final RemotingCommandType TYPE_DEFAULT = RemotingCommandType.REQUEST_COMMAND;
	private static final boolean RESPONSIBLE_DEFAULT = false;

	public boolean success;

	public String message;

	public String payload;

	@Override
	public int requestCode() {
		return RequestCode.CARRIER.getCode();
	}

	@Override
	public boolean responsible() {
		return RESPONSIBLE_DEFAULT;
	}

	@Override
	public RemotingCommandType remotingCommandType() {
		return TYPE_DEFAULT;
	}

}
