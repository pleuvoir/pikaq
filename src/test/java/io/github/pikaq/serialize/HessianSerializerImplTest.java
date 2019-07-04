package io.github.pikaq.serialize;

import org.junit.Assert;
import org.junit.Test;

import io.github.pikaq.serialize.hessian.HessianSerializerImpl;

public class HessianSerializerImplTest {

	@Test
	public void test() {
		HessianSerializerImpl impl = new HessianSerializerImpl();
		SerializerAlgorithm serializerAlgorithm = impl.getSerializerAlgorithm();
		Assert.assertEquals(serializerAlgorithm.getCode(), SerializerAlgorithm.HESSIAN.getCode());

		UserDTO userDTO = new UserDTO();
		userDTO.setAge(18);
		userDTO.setName("pleuvoir");

		byte[] bytes = impl.serialize(userDTO);

		UserDTO deserialize = impl.deserialize(bytes, UserDTO.class);

		Assert.assertEquals(userDTO.getName(), deserialize.getName());
		Assert.assertEquals(userDTO.getAge(), deserialize.getAge());

	}

}
