package io.github.pikaq.network.server;

import io.github.pikaq.network.HostAndPort;
import io.github.pikaq.network.NetworkLocationEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "create")
public class NetworkServerContext {

	private HostAndPort hostAndPort;

	private ServerConfig serverConfig;
	
	private final NetworkLocationEnum networkLocation = NetworkLocationEnum.SERVER;
}
