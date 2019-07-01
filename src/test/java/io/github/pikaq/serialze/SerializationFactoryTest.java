package io.github.pikaq.serialze;

import org.junit.Assert;
import org.junit.Test;

import io.github.pikaq.serialize.ISerializer;
import io.github.pikaq.serialize.SerializationFactory;
import io.github.pikaq.serialize.SerializerAlgorithm;

public class SerializationFactoryTest {

	@Test
	public void test() {

		ISerializer defaultImpl = SerializationFactory.defaultImpl();

		Assert.assertEquals(defaultImpl.getSerializerAlgorithm(), SerializerAlgorithm.JSON);

		ISerializer iSerializer = SerializationFactory.get(SerializerAlgorithm.JSON);
		Assert.assertEquals(iSerializer.getSerializerAlgorithm(), SerializerAlgorithm.JSON);
	}
}
