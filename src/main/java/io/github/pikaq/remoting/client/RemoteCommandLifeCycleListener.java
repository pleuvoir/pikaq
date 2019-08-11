package io.github.pikaq.remoting.client;

import io.github.pikaq.remoting.protocol.command.RemotingCommand;
import lombok.extern.slf4j.Slf4j;

public interface RemoteCommandLifeCycleListener {

	@Slf4j
	public class Adapter implements RemoteCommandLifeCycleListener {

		@Override
		public void beforeSend(RemotingCommand request) {
			log.info("beforeSend - request：{}", request.toJSON());
		}

		@Override
		public void afterSend(RemotingCommand request) {
			log.info("afterSend - request：{}", request.toJSON());
		}

		@Override
		public void sendComplete(RemotingCommand request) {
			log.info("sendComplete - request：{}", request.toJSON());
		}

		@Override
		public void sendException(RemotingCommand request, Throwable ex) {
			log.error("[sendException]发送报文失败：{}，cause：{}", request.toJSON(), ex);
		}

		@Override
		public void beforeHandler(RemotingCommand response) {
			log.info("beforeHandler - response：{}", response.toJSON());
		}

		@Override
		public void afterHandler(RemotingCommand response) {
			log.info("afterHandler - response：{}", response.toJSON());
		}

	}

	void beforeSend(RemotingCommand request);

	void afterSend(RemotingCommand request);

	void sendComplete(RemotingCommand request);

	void sendException(RemotingCommand request, Throwable ex);

	void beforeHandler(RemotingCommand response);

	void afterHandler(RemotingCommand response);

}
