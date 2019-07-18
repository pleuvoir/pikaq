package io.github.pikaq.network;

import io.github.pikaq.network.client.ClientConfig;
import io.github.pikaq.network.server.ServerConfig;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "create")
public class RemotingContext {

	private Channel channel;
	private ServerConfig serverConfig;
	private ClientConfig clientConfig;

}
