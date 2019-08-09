package io.github.pikaq.common.util;

import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Maps;

public class SingletonFactoy {

	static ConcurrentMap<Class<?>, Object> caches = Maps.newConcurrentMap();

	@SuppressWarnings("unchecked")
	public static <T> T get(Class<T> t) {
		Object object = caches.get(t);
		if (object == null) {
			throw new IllegalStateException(t + " not register!");
		}
		return (T) object;
	}

	public static <T> void register(Class<T> t, T obj) {
		Object prev = caches.putIfAbsent(t, obj);
		if (prev != null) {
			throw new IllegalStateException(t + " has already register!");
		}
	}

}
