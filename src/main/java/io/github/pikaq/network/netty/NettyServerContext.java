package io.github.pikaq.network.netty;

import io.github.pikaq.network.server.ServerConfig;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "create")
public class NettyServerContext {

	private Channel channel;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workGroup;
	private ServerConfig serverConfig;

}
