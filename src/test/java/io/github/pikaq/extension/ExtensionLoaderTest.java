package io.github.pikaq.extension;

import org.junit.Test;

public class ExtensionLoaderTest {

	@Test
	public void test(){
		ExtensionLoader.initExtension(EchoService.class);
		
		EchoService extensionImpl = ExtensionLoader.getDafaultExtensionImpl(EchoService.class);
		System.out.println(extensionImpl);
	}
}
