package io.github.pikaq;


import io.github.pikaq.client.ClientConfig;
import io.github.pikaq.server.ServerConfig;
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
