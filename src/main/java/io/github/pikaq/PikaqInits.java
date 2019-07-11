package io.github.pikaq;

import io.github.pikaq.extension.ExtensionLoader;
import io.github.pikaq.serialization.Serializer;

public class PikaqInits {

	public static void initModules() {
		// [*]初始化序列化
		ExtensionLoader.initExtension(Serializer.class);
	}

}
