package io.github.pikaq.remoting;

public class RemoteSendException extends Exception {

	private static final long serialVersionUID = -7486436135397777942L;

	public RemoteSendException() {
		super();
	}

	public RemoteSendException(String message, Throwable cause) {
		super(message, cause);
	}

	public RemoteSendException(String message) {
		super(message);
	}

	public RemoteSendException(Throwable cause) {
		super(cause);
	}

}
