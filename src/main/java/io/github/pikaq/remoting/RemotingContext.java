package io.github.pikaq.remoting;

import io.github.pikaq.remoting.client.ClientConfig;
import io.github.pikaq.remoting.server.ServerConfig;
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
