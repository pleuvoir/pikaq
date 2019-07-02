package io.github.pikaq.serialize.fastjson;

import com.alibaba.fastjson.JSON;

import io.github.pikaq.serialize.ISerializer;
import io.github.pikaq.serialize.SerializationException;
import io.github.pikaq.serialize.SerializerAlgorithm;

/**
 * FastJSON序列化实现
 * @author pleuvoir
 *
 */
public class FastJSONSerializerImpl implements ISerializer {

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
