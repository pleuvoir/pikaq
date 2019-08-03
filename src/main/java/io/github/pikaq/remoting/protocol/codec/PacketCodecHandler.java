package io.github.pikaq.remoting.protocol.codec;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import io.github.pikaq.common.util.PacketCodecUtils;
import io.github.pikaq.remoting.protocol.command.RemoteCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, RemoteCommand> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) {
		RemoteCommand response = PacketCodecUtils.decode(byteBuf);
		out.add(response);
		LOG.debug("解码成功。{}", JSON.toJSONString(response));
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, RemoteCommand request, List<Object> out) {
		ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
		PacketCodecUtils.encode(byteBuf, request);
		out.add(byteBuf);
		LOG.debug("编码成功。{}", JSON.toJSONString(request));
	}

	public static final PacketCodecHandler INSTANCE = new PacketCodecHandler();
	
	private static final Logger LOG = LoggerFactory.getLogger(PacketCodecHandler.class);
}