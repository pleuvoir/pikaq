package io.github.pikaq.remoting;

public class RemoteCommandException extends RuntimeException {

	private static final long serialVersionUID = -7486436135397777942L;

	public RemoteCommandException() {
		super();
	}

	public RemoteCommandException(String message, Throwable cause) {
		super(message, cause);
	}

	public RemoteCommandException(String message) {
		super(message);
	}

	public RemoteCommandException(Throwable cause) {
		super(cause);
	}

}
