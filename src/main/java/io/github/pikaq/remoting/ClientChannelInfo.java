package io.github.pikaq.remoting;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientChannelInfo {

	private final Channel channel;
	private final String clientId;
}
