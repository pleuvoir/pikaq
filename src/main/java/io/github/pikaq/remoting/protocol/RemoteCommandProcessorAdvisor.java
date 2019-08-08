package io.github.pikaq.remoting.protocol;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import io.github.pikaq.remoting.protocol.command.RemoteCommand;

public class RemoteCommandProcessorAdvisor implements InvocationHandler {

	private RemoteCommandProcessorProxy proxy;

	private Object target;

	private RemoteCommand request;

	public RemoteCommandProcessorAdvisor(Object target, RemoteCommandProcessorProxy proxy, RemoteCommand request) {
		this.proxy = proxy;
		this.request = request;
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		this.proxy.deBeforeRequest(request);
		Object result = method.invoke(this.target, args);
		this.proxy.deAfterRequest(request);
		return result;
	}

}
