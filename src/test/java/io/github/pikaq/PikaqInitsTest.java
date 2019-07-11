package io.github.pikaq;


import io.github.pikaq.extension.ExtensionLoader;
import io.github.pikaq.serialization.Serializer;

public class PikaqInitsTest {

	
	public static void main(String[] args) {
		PikaqInits.initModules();
		Serializer dafaultExtensionImpl = ExtensionLoader.getDafaultExtensionImpl(Serializer.class);
		System.out.println(dafaultExtensionImpl);
	}
}
