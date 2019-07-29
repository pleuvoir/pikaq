package io.github.pikaq.remoting.protocol;

import io.github.pikaq.remoting.protocol.RemoteCommand.Command;
import io.github.pikaq.remoting.protocol.builder.DefaultPacketBuilder;
import io.github.pikaq.remoting.protocol.builder.PacketBuilder;
import io.github.pikaq.serialization.SerializationFactory;
import io.github.pikaq.serialization.Serializer;
import io.netty.buffer.ByteBuf;


/**
 * Packet编解码器
 */
public class PacketCodecHelper {

    public static final PacketCodecHelper INSTANCE = new PacketCodecHelper();

    private final PacketBuilder packetBuilder = new DefaultPacketBuilder();
    
    /**
     * 魔法数字
     */
    private static final int MAGIC_NUMBER = 9527;

    /**
     * 序列化
     */
	public void encode(ByteBuf buffer, RemoteCommand remoteCommand) {
    	Packet packet = this.packetBuilder.build(remoteCommand);
        final byte[] bytes = SerializationFactory.defaultImpl().serialize(packet);
        // 魔数
        buffer.writeInt(MAGIC_NUMBER);
        //指令
        buffer.writeInt(remoteCommand.getCmd().getCode());
        //序列化算法
        buffer.writeByte(SerializationFactory.defaultImpl().getSerializerAlgorithm().getCode());
        //长度位
        buffer.writeInt(bytes.length);
        // 内容
        buffer.writeBytes(bytes);
    }

    public Packet decode(ByteBuf byteBuf) {
        //跳过前4个字节（一个Int）的魔数
        byteBuf.skipBytes(4);
        //指令
        int cmdCode = byteBuf.readInt();
        //序列化算法
        byte algorithmCode = byteBuf.readByte();
        //长度位
        int length = byteBuf.readInt();
        //读取数据
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        //获取指令对应的实体类
        Class<? extends Packet> packetClazz = this.packetBuilder.get(Command.toEnum(cmdCode));
        //获取序列化器进行反序列化
        final Serializer serializer = SerializationFactory.get(algorithmCode);
        return  serializer.deserialize(bytes, packetClazz);
    }

}
