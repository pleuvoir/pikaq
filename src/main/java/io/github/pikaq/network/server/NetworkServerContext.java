package io.github.pikaq.network.server;

import io.github.pikaq.network.HostAndPort;
import io.github.pikaq.network.NetworkLocationEnum;
import io.github.pikaq.network.ServerState;
import io.github.pikaq.network.netty.NettyServerContext;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "create")
public class NetworkServerContext {

	private HostAndPort hostAndPort;

	private NettyServerContext nettyServerContext;
	
	private ServerState serverState;
	
	private final NetworkLocationEnum networkLocation = NetworkLocationEnum.SERVER;
}
