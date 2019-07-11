package io.github.pikaq.serialization.hessian;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;

import io.github.pikaq.serialization.SerializationException;
import io.github.pikaq.serialization.Serializer;
import io.github.pikaq.serialization.SerializerAlgorithm;

/**
 * Hessian序列化实现，待传输的类必须实现{@link #Serializable}接口
 * @author pleuvoir
 *
 */
public class HessianSerializerImpl implements Serializer {

	private SerializerFactory serializerFactory = new SerializerFactory();

	@Override
	public SerializerAlgorithm getSerializerAlgorithm() {
		return SerializerAlgorithm.HESSIAN;
	}

	@Override
	public byte[] serialize(Object obj) throws SerializationException {
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		Hessian2Output output = new Hessian2Output(byteArray);
		output.setSerializerFactory(serializerFactory);
		try {
			output.writeObject(obj);
			output.close();
		} catch (IOException e) {
			throw new SerializationException("IOException occurred when Hessian serializer encode!", e);
		}

		return byteArray.toByteArray();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws SerializationException {
		Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(bytes));
		input.setSerializerFactory(this.serializerFactory);
		Object resultObject;
		try {
			resultObject = input.readObject();
			input.close();
		} catch (IOException e) {
			throw new SerializationException("IOException occurred when Hessian serializer decode!", e);
		}
		return (T) resultObject;
	}

}
