package io.github.pikaq.common.util;

import io.github.pikaq.remoting.CommandCode;
import io.github.pikaq.remoting.RemoteCommandFactory;
import io.github.pikaq.remoting.protocol.Packet;
import io.github.pikaq.serialization.SerializationFactory;
import io.github.pikaq.serialization.Serializer;
import io.netty.buffer.ByteBuf;


/**
 * Packet编解码器
 */
public class PacketCodecUtils {

    /**
     * 魔法数字
     */
    private static final int MAGIC_NUMBER = 9527;

    /**
     * 序列化
     */
    
	public static void encode(ByteBuf buffer, Packet packet) {
		Serializer defaultImpl = SerializationFactory.defaultImpl();
        final byte[] bytes = defaultImpl.serialize(packet);
        // 魔数
        buffer.writeInt(MAGIC_NUMBER);
        //指令
         buffer.writeInt(RemoteCommandFactory.getCommand(packet.getClass()).getCode());
        //序列化算法
        buffer.writeInt(defaultImpl.getSerializerAlgorithm().getCode());
        //长度位
        buffer.writeInt(bytes.length);
        // 内容
        buffer.writeBytes(bytes);
    }

	@SuppressWarnings("unchecked")
    public static Packet decode(ByteBuf byteBuf) {
        //跳过前4个字节（一个Int）的魔数
        byteBuf.skipBytes(4);
        //指令
        int cmdCode = byteBuf.readInt();
        //序列化算法
        int algorithmCode = byteBuf.readInt();
        //长度位
        int length = byteBuf.readInt();
        //读取数据
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        //获取指令对应的实体类
		Class<? extends Packet> packetClazz = RemoteCommandFactory.select(CommandCode.toEnum(cmdCode)).getPacketClazz();
        //获取序列化器进行反序列化
        final Serializer serializer = SerializationFactory.get(algorithmCode);
        return  serializer.deserialize(bytes, packetClazz);
    }

}
