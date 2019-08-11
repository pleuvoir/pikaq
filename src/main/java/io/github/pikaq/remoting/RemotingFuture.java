package io.github.pikaq.remoting;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.github.pikaq.remoting.protocol.command.RemotingCommand;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 异步发送占位
 * 
 * @author pleuvoir
 *
 */
@Data
@Slf4j
public class RemotingFuture {

	private final String messageId;
	private RemotingCommand responseCommand;
	private final Long timeoutMillis;
	private final CountDownLatch countDownLatch = new CountDownLatch(1);
	private final InvokeCallback invokeCallback;
	private volatile boolean sendRequestOK = true;
	private volatile Throwable cause;
	private final long beginTimestamp = System.currentTimeMillis();

	/**
	 * 阻塞等待返回结果
	 */
	public RemotingCommand waitResponse(final long timeoutMillis) {
		try {
			this.countDownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
		} catch (InterruptedException ignore) {
		}
		return this.responseCommand;
	}

	/**
	 * 完成结果响应，一般是正常流程使用 <b>注意：此操作会结束 {@link #waitResponse(long)}的等待</b>
	 */
	public void completeResponse(final RemotingCommand responseCommand) {
		this.responseCommand = responseCommand;
		this.countDownLatch.countDown();
	}

	/**
	 * 执行回调
	 */
	public void onReceiveResponse() {
		try {
			this.invokeCallback.onReceiveResponse(this);
		} catch (Throwable e) {
			log.error("executeInvokeCallback error, ", e);
		}
	}
	
	
	/**
	 * 执行回调
	 */
	public void onRequestException() {
		try {
			this.invokeCallback.onRequestException(this);
		} catch (Throwable e) {
			log.error("executeInvokeCallback error, ", e);
		}
	}

	public RemotingFuture(String messageId, Long timeoutMillis, InvokeCallback invokeCallback) {
		super();
		this.messageId = messageId;
		this.timeoutMillis = timeoutMillis;
		this.invokeCallback = invokeCallback;
	}
}
