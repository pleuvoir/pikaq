package io.github.pikaq.network.client;

import io.github.pikaq.network.HostAndPort;
import io.github.pikaq.network.NetworkLocationEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "create")
public class NetworkClientContext {

	private HostAndPort hostAndPort;

	private ClientConfig clientConfig;

	private final NetworkLocationEnum networkLocation = NetworkLocationEnum.CLIENT;
}
