package io.github.pikaq.protocol.command.body;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CarrierCommandBody<T> implements RemotingCommandBody {

	private boolean success;

	private String message;

	private T payload;

	public static CarrierCommandBody<String> buildString(boolean success, String message, String payload) {
		return CarrierCommandBody.<String>builder().success(success).message(message).payload(payload).build();
	}
}
