package io.github.pikaq.common.util;

import java.net.SocketAddress;

import org.apache.commons.lang3.StringUtils;

import io.netty.channel.Channel;

public class RemotingUtils {

	/**
	 * 转换为远程地址
	 */
	public static String parseChannelRemoteAddr(final Channel channel) {
		if (null == channel) {
			return StringUtils.EMPTY;
		}
		SocketAddress remote = channel.remoteAddress();
		final String addr = remote != null ? remote.toString() : "";

		if (addr.length() > 0) {
			int index = addr.lastIndexOf("/");
			if (index >= 0) {
				return addr.substring(index + 1);
			}

			return addr;
		}
		return StringUtils.EMPTY;
	}
}
