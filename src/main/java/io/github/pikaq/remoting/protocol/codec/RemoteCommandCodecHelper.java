package io.github.pikaq.remoting.protocol.codec;

import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.remoting.protocol.command.RemotingCommand;
import io.github.pikaq.remoting.protocol.command.RemoteCommandFactory;
import io.github.pikaq.serialization.SerializationFactory;
import io.github.pikaq.serialization.Serializer;
import io.netty.buffer.ByteBuf;


/**
 * RemoteCommand编解码器
 */
public class RemoteCommandCodecHelper {

    /**
     * 魔法数字
     */
    private static final int MAGIC_NUMBER = 9527;

    /**
     * 编码
     */
	public static void encode(ByteBuf buffer, RemotingCommand command) {
		Serializer defaultImpl = SerializationFactory.defaultImpl();
        final byte[] bytes = defaultImpl.serialize(command);
        // 魔数
        buffer.writeInt(MAGIC_NUMBER);
        //指令
         buffer.writeInt(command.getRequestCode());
        //序列化算法
        buffer.writeInt(defaultImpl.getSerializerAlgorithm().getCode());
        //长度位
        buffer.writeInt(bytes.length);
        // 内容
        buffer.writeBytes(bytes);
    }

	/**
	 * 解码
	 */
    public static RemotingCommand decode(ByteBuf byteBuf) {
        //跳过前4个字节（一个Int）的魔数
        byteBuf.skipBytes(4);
        //指令
        int requestCode = byteBuf.readInt();
        //序列化算法
        int algorithmCode = byteBuf.readInt();
        //长度位
        int length = byteBuf.readInt();
        //读取数据
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        //获取指令对应的实体类
		Class<? extends RemotingCommand> cmdClazz = SingletonFactoy.get(RemoteCommandFactory.class).fromRequestCode(requestCode);
        //获取序列化器进行反序列化
        final Serializer serializer = SerializationFactory.get(algorithmCode);
        return  serializer.deserialize(bytes,cmdClazz);
    }

}
