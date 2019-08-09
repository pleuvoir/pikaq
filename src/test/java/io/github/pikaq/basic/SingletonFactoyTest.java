package io.github.pikaq.basic;

import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.remoting.RemoteCommandLifeCycleListener;

public class SingletonFactoyTest {

	public static void main(String[] args) {

		SingletonFactoy.register(RemoteCommandLifeCycleListener.class, new RemoteCommandLifeCycleListener.Adapter());
		
		
		RemoteCommandLifeCycleListener remoteCommandLifeCycleListener = SingletonFactoy.get(RemoteCommandLifeCycleListener.class);
		
		System.out.println(remoteCommandLifeCycleListener);
	}
}
