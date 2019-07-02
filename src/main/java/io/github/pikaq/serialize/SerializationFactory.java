package io.github.pikaq.serialize;

import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentSkipListMap;

import io.github.pikaq.PikaqConst;

/**
 * 序列化工厂，默认使用SPI中第一个实现
 * @author pleuvoir
 *
 */
public class SerializationFactory {

	private SerializationFactory() {
	}

	private static final ISerializer DEFAULT;

	private static ConcurrentSkipListMap<SerializerAlgorithm, ISerializer> serializerTable = new ConcurrentSkipListMap<>();

	static {
		ServiceLoader<ISerializer> factories = ServiceLoader.load(ISerializer.class);
		for (ISerializer serializer : factories) {
			serializerTable.put(SerializerAlgorithm.toAlgorithm(serializer.getSerializerAlgorithm().getCode()),
					serializer);
		}
		
		// 优先取系统变量中设置的序列化算法，然后是SPI实现的第一个
		String algorithProperty = System.getProperty(PikaqConst.SERIALIZER);
		SerializerAlgorithm algorithm = SerializerAlgorithm.toAlgorithm(algorithProperty);
		if (algorithm == null) {
			DEFAULT = serializerTable.firstEntry().getValue();
		} else {
			DEFAULT = serializerTable.get(algorithm);
		}
	}

	/**
	 * 获取对应的序列化实现
	 */
	public static ISerializer get(SerializerAlgorithm algorithm) {
		return serializerTable.get(algorithm);
	}

	/**
	 * 获取对应的序列化实现，获取失败抛出异常
	 */
	public static ISerializer get(int algorithmCode) {
		SerializerAlgorithm algorithm = SerializerAlgorithm.toAlgorithm(algorithmCode);
		if (algorithm == null) {
			throw new SerializationException("获取序列化实现失败，algorithmCode=" + algorithmCode);
		}
		return get(algorithm);
	}

	/**
	 * 获取默认的序列化实现
	 */
	public static ISerializer defaultImpl() {
		return DEFAULT;
	}
}
