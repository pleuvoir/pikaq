package io.github.pikaq.network;

public interface Networkable {

	HostAndPort getHostAndPort();
	
	NetworkLocationEnum networkLocation();
}
