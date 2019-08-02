package io.github.pikaq.remoting.protocol.codec;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import io.github.pikaq.common.util.PacketCodecUtils;
import io.github.pikaq.remoting.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) {
		Packet packet = PacketCodecUtils.decode(byteBuf);
		out.add(packet);
		LOG.debug("解码成功。{}", JSON.toJSONString(packet));
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> out) {
		ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
		PacketCodecUtils.encode(byteBuf, packet);
		out.add(byteBuf);
		LOG.debug("编码成功。{}", JSON.toJSONString(packet));
	}

	public static final PacketCodecHandler INSTANCE = new PacketCodecHandler();
	
	private static final Logger LOG = LoggerFactory.getLogger(PacketCodecHandler.class);
}