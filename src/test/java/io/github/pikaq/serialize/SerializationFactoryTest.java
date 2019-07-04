package io.github.pikaq.serialize;

import org.junit.Assert;
import org.junit.Test;

import io.github.pikaq.PikaqConst;

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
