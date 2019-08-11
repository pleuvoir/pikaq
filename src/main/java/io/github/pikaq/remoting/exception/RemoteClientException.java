package io.github.pikaq.remoting.exception;

public class RemoteClientException extends RuntimeException {

	private static final long serialVersionUID = -7486436135397777942L;

	public RemoteClientException() {
		super();
	}

	public RemoteClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public RemoteClientException(String message) {
		super(message);
	}

	public RemoteClientException(Throwable cause) {
		super(cause);
	}

}
