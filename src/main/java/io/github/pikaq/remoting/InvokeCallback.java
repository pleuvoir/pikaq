package io.github.pikaq.remoting;

/**
 * 异步回调，发送异步请求时使用
 * 
 * @author pleuvoir
 *
 */
public interface InvokeCallback {

	/**
	 * 接收到响应时触发
	 */
	void onReceiveResponse(final RemotingFuture remotingFuture);

	/**
	 * 发送异常时触发
	 */
	void onRequestException(final RemotingFuture remotingFuture);
}
