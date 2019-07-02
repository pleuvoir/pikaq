package io.github.pikaq.invokeContext;

import org.junit.Assert;
import org.junit.Test;

import io.github.pikaq.InvokeContext;

public class InvokeContextTest {

	@Test
	public void test() {
		InvokeContext context = new InvokeContext();
		context.put(InvokeContext.CLIENT_LOCAL_IP, "127.0.0.1");
		Assert.assertEquals(context.get(InvokeContext.CLIENT_LOCAL_IP), "127.0.0.1");
		
		context.putIfAbsent(InvokeContext.CLIENT_LOCAL_IP, "192.168.1.1");
		Assert.assertEquals(context.get(InvokeContext.CLIENT_LOCAL_IP), "127.0.0.1");
		
		context.put(InvokeContext.CLIENT_LOCAL_PORT, "8080");
		
		String orDefault = context.getOrDefault(InvokeContext.CLIENT_LOCAL_PORT,"8080");
		
		Assert.assertEquals(orDefault, "8080");
	}
}
