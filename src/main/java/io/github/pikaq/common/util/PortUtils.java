package io.github.pikaq.common.util;

import java.io.IOException;
import java.net.ServerSocket;

public class PortUtils {

	/**
	 * 获取一个可用的端口
	 * @param port 当前输入的端口，如果可用则直接返回
	 * @return 可用的端口
	 */
	public static int checkAvailablePort(int port) {
		while (port < 65500) {
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(port);
				return port;
			} catch (IOException ignore) {
			} finally {
				if (serverSocket != null) {
					try {
						serverSocket.close();
					} catch (IOException ignore) {
					}
				}
			}
			port++;
		}
		throw new RuntimeException("没有可用的端口");
	}

	/**
	 * 检查端口是否可用
	 */
	public static boolean checkPortAvailable(int port) {
		if (port < 65500) {
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(port);
				return true;
			} catch (IOException ignore) {
				return false;
			} finally {
				if (serverSocket != null) {
					try {
						serverSocket.close();
					} catch (IOException ignore) {
					}
				}
			}
		}
		return false;
	}
}
