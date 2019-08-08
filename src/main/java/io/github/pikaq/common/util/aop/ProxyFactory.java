package io.github.pikaq.common.util.aop;

import java.lang.reflect.InvocationHandler;

public interface ProxyFactory {

	<T> T getProxy(Class<T> serviceInterface, InvocationHandler h);

	<T> T getProxy(ClassLoader classLoader, Class<T> serviceInterface, InvocationHandler h);
}
