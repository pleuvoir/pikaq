package io.github.pikaq.remoting;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.github.pikaq.common.util.Generator;
import io.github.pikaq.remoting.protocol.Packet;

/**
 * 远程响应命令
 * @author pleuvoir
 *
 */
public abstract class RemoteResponseCommand<P extends Packet> implements RemoteCommand<Packet> {

	@Override
	public boolean isRequest() {
		return false;
	}

	@Override
	public String id() {
		return Generator.nextUUID();
	}

	// 因为抽象类中并未有实际的泛型实现类，所以通过反射获取真实类型
	@SuppressWarnings("unchecked")
	@Override
	public Class<Packet> getPacketClazz() {
		ParameterizedType parametclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = parametclass.getActualTypeArguments();
		Class<Packet> clazz = (Class<Packet>) actualTypeArguments[0];
		return clazz;
	}

}
