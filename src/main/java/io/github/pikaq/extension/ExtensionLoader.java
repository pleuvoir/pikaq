package io.github.pikaq.extension;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SPI简单增强，提供了指定默认实现的功能
 * @author pleuvoir
 *
 */
public class ExtensionLoader {

	private static Map<String, Object> name2ImplTable = new ConcurrentHashMap<>(8); // 全限定名，实现类
	private static Map<Class<?>, Object> inteface2DefaultImplTable = new ConcurrentHashMap<>(); // 接口，默认实现类

	/**
	 * 初始化SPI
	 */
	public static void initExtension(Class<?> interfaceClazz) {
		ServiceLoader<?> factories = ServiceLoader.load(interfaceClazz);
		for (Object impl : factories) {
			String defaultClassName = interfaceClazz.getDeclaredAnnotation(SPI.class).value();
			String implClassName = impl.getClass().getName();
			if (defaultClassName.trim().equals(implClassName)) {
				inteface2DefaultImplTable.put(interfaceClazz, impl);
			}
			name2ImplTable.put(implClassName, impl);
		}
	}

	/**
	 * 获取SPI接口默认的实现
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getDafaultExtensionImpl(Class<T> interfaceClazz) {
		Object impl = inteface2DefaultImplTable.get(interfaceClazz);
		return (T) impl;
	}
}
