package io.github.pikaq.serialization;

import io.github.pikaq.extension.SPI;

/**
 * 序列化接口。提供了对象序列化及反序列化功能
 * @author pleuvoir
 *
 */
@SPI("io.github.pikaq.serialization.fastjson.FastjsonSerializer")
public interface Serializer {

	/**
	 * 序列化算法，{@link SerializerAlgorithm}
	 */
	SerializerAlgorithm getSerializerAlgorithm();

	/**
	 * 对象转为字节数组
	 */
	byte[] serialize(final Object obj) throws SerializationException;

	/**
	 * 字节数组转为对象
	 */
	<T> T deserialize(final byte[] bytes, final Class<T> clazz) throws SerializationException;

}
