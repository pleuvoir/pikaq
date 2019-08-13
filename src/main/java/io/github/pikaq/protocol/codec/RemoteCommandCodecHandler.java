package io.github.pikaq.protocol.codec;

import com.alibaba.fastjson.JSON;
import io.github.pikaq.protocol.command.RemotingCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ChannelHandler.Sharable
public class RemoteCommandCodecHandler extends MessageToMessageCodec<ByteBuf, RemotingCommand> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) {
		RemotingCommand response = RemoteCommandCodecHelper.decode(byteBuf);
		out.add(response);
		LOG.debug("解码成功。{}", JSON.toJSONString(response));
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, RemotingCommand request, List<Object> out) {
		ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
		RemoteCommandCodecHelper.encode(byteBuf, request);
		out.add(byteBuf);
		LOG.debug("编码成功。{}", JSON.toJSONString(request));
	}

	public static final RemoteCommandCodecHandler INSTANCE = new RemoteCommandCodecHandler();
	
	private static final Logger LOG = LoggerFactory.getLogger(RemoteCommandCodecHandler.class);
}