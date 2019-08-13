package io.github.pikaq.serialization;

import io.github.pikaq.PikaqConst;
import io.github.pikaq.protocol.serialization.SerializationFactory;
import io.github.pikaq.protocol.serialization.Serializer;
import io.github.pikaq.protocol.serialization.SerializerAlgorithm;
import org.junit.Assert;
import org.junit.Test;

public class SerializationFactoryTest {

	@Test
	public void testDafault() {

		Serializer defaultImpl = SerializationFactory.defaultImpl();

		Assert.assertEquals(defaultImpl.getSerializerAlgorithm(), SerializerAlgorithm.JSON);

		Serializer iSerializer = SerializationFactory.get(SerializerAlgorithm.JSON);
		Assert.assertEquals(iSerializer.getSerializerAlgorithm(), SerializerAlgorithm.JSON);

		System.setProperty(PikaqConst.SERIALIZER, "2");
		
	}
	

	@Test
	public void testSystemNotImpl() {


		System.setProperty(PikaqConst.SERIALIZER, "2");

		Serializer defaultImpl = SerializationFactory.defaultImpl();

		Assert.assertEquals(defaultImpl.getSerializerAlgorithm(), SerializerAlgorithm.JSON);

		Serializer iSerializer = SerializationFactory.get(SerializerAlgorithm.JSON);
		Assert.assertEquals(iSerializer.getSerializerAlgorithm(), SerializerAlgorithm.JSON);
	}
	
	@Test
	public void testSystemExist() {

		System.setProperty(PikaqConst.SERIALIZER, "0");

		Serializer defaultImpl = SerializationFactory.defaultImpl();

		Assert.assertEquals(defaultImpl.getSerializerAlgorithm(), SerializerAlgorithm.JSON);

		Serializer iSerializer = SerializationFactory.get(SerializerAlgorithm.JSON);
		Assert.assertEquals(iSerializer.getSerializerAlgorithm(), SerializerAlgorithm.JSON);
		
	}
	
	
	@Test
	public void testHessian() {

		System.setProperty(PikaqConst.SERIALIZER, String.valueOf(SerializerAlgorithm.HESSIAN.getCode()));

		Serializer defaultImpl = SerializationFactory.defaultImpl();

		Assert.assertEquals(defaultImpl.getSerializerAlgorithm(), SerializerAlgorithm.HESSIAN);

	}
}
