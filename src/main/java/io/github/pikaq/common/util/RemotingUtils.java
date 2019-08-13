package io.github.pikaq.common.util;

import java.net.InetSocketAddress;
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

	/**
	 * 转换为远程连接地址
	 */
	public static SocketAddress string2SocketAddress(final String addr) {
		String[] s = addr.split(":");
		InetSocketAddress isa = new InetSocketAddress(s[0], Integer.parseInt(s[1]));
		return isa;
	}
}
