package io.github.pikaq.serialization;

import org.junit.Assert;
import org.junit.Test;

import io.github.pikaq.serialization.hessian.HessianSerializerImpl;

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
