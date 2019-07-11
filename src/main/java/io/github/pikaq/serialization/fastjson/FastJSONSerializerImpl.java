package io.github.pikaq.serialization.fastjson;

import com.alibaba.fastjson.JSON;

import io.github.pikaq.serialization.SerializationException;
import io.github.pikaq.serialization.Serializer;
import io.github.pikaq.serialization.SerializerAlgorithm;

/**
 * FastJSON序列化实现
 * @author pleuvoir
 *
 */
public class FastJSONSerializerImpl implements Serializer {

	@Override
	public SerializerAlgorithm getSerializerAlgorithm() {
		return SerializerAlgorithm.JSON;
	}

	@Override
	public byte[] serialize(Object obj) throws SerializationException {
		return JSON.toJSONBytes(obj);
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws SerializationException {
		return JSON.parseObject(bytes, clazz);
	}

}
