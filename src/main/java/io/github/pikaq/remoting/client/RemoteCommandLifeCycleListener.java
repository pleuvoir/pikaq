package io.github.pikaq.remoting.client;

import io.github.pikaq.remoting.protocol.command.RemoteCommand;
import lombok.extern.slf4j.Slf4j;

public interface RemoteCommandLifeCycleListener {

	@Slf4j
	public class Adapter implements RemoteCommandLifeCycleListener {

		@Override
		public void beforeSend(RemoteCommand request) {
			log.info("beforeSend - request：{}", request.toJSON());
		}

		@Override
		public void afterSend(RemoteCommand request) {
			log.info("afterSend - request：{}", request.toJSON());
		}

		@Override
		public void sendComplete(RemoteCommand request) {
			log.info("sendComplete - request：{}", request.toJSON());
		}

		@Override
		public void sendException(RemoteCommand request, Throwable ex) {
			log.error("[sendException]发送报文失败：{}，cause：{}", request.toJSON(), ex);
		}

		@Override
		public void beforeHandler(RemoteCommand response) {
			log.info("beforeHandler - response：{}", response.toJSON());
		}

		@Override
		public void afterHandler(RemoteCommand response) {
			log.info("afterHandler - response：{}", response.toJSON());
		}

	}

	void beforeSend(RemoteCommand request);

	void afterSend(RemoteCommand request);

	void sendComplete(RemoteCommand request);

	void sendException(RemoteCommand request, Throwable ex);

	void beforeHandler(RemoteCommand response);

	void afterHandler(RemoteCommand response);

}
