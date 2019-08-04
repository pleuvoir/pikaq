package io.github.pikaq.remoting.protocol.command;

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
@NoArgsConstructor
@AllArgsConstructor
public class CarrierCommand<T> extends RemoteBaseCommand {

	private boolean success;

	private String message;

	private T payload;

	@Override
	public int getSymbol() {
		return CommandCode.CARRIER.getCode();
	}

	@Override
	public CommandCodeType getCommandCodeType() {
		return CommandCodeType.SYSTEM;
	}

	public static CarrierCommand<String> buildString(boolean success, String message, String payload) {
		return CarrierCommand.<String>builder()
				.success(success)
				.message(message)
				.payload(payload)
				.build();
	}

}
