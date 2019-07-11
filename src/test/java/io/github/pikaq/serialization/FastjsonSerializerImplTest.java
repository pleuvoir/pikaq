package io.github.pikaq.serialization;

import org.junit.Assert;
import org.junit.Test;

import io.github.pikaq.serialization.SerializerAlgorithm;
import io.github.pikaq.serialization.fastjson.FastJSONSerializerImpl;

public class FastjsonSerializerImplTest {

	@Test
	public void test() {
		FastJSONSerializerImpl fastjsonSerializerImpl = new FastJSONSerializerImpl();
		SerializerAlgorithm serializerAlgorithm = fastjsonSerializerImpl.getSerializerAlgorithm();
		Assert.assertEquals(serializerAlgorithm.getCode(), SerializerAlgorithm.JSON.getCode());

		UserDTO userDTO = new UserDTO();
		userDTO.setAge(18);
		userDTO.setName("pleuvoir");

		byte[] bytes = fastjsonSerializerImpl.serialize(userDTO);

		UserDTO deserialize = fastjsonSerializerImpl.deserialize(bytes, UserDTO.class);

		Assert.assertEquals(userDTO.getName(), deserialize.getName());
		Assert.assertEquals(userDTO.getAge(), deserialize.getAge());

	}

}
