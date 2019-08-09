//package io.github.pikaq.basic;
//
//import io.github.pikaq.common.util.aop.JdkProxyFactory;
//import io.github.pikaq.common.util.aop.ProxyFactory;
//import io.github.pikaq.remoting.protocol.RemoteCommandProcessor;
//import io.github.pikaq.remoting.protocol.RemoteCommandProcessorAdvisor;
//import io.github.pikaq.remoting.protocol.command.PingCommand;
//
//public class ProxyFactoryTest {
//
//	public static void main(String[] args) {
//
//		// import javassist.util.proxy.ProxyFactory;
//		ProxyFactory proxyFactory = new JdkProxyFactory();
//
//		PingCommand request = new PingCommand();
//		request.setId("1");
//
//		RemoteCommandProcessorAdvisor h = new RemoteCommandProcessorAdvisor(new HeartRemoteCommandProcessor(),
//				new NOPRemoteCommandProcessorProxy(), request);
//
//		//必须使用这个类加载器interface io.github.pikaq.remoting.protocol.RemoteCommandProcessor is not visible from class loader
//		RemoteCommandProcessor proxy = proxyFactory.getProxy(ProxyFactoryTest.class.getClassLoader(),
//				RemoteCommandProcessor.class, h);
//		
//		proxy.handler(null, request);
//	}
//
//}
