package io.github.pikaq.common.util.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class JdkProxyFactory implements ProxyFactory {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getProxy(ClassLoader classLoader, Class<T> serviceInterface, InvocationHandler h) {
		return (T) Proxy.newProxyInstance(classLoader, new Class[] { serviceInterface }, h);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getProxy(Class<T> serviceInterface, InvocationHandler h) {
		return (T) Proxy.newProxyInstance(serviceInterface.getClass().getClassLoader(),
				new Class[] { serviceInterface }, h);
	}

}
