package io.github.pikaq.remoting.exception;

public class RemotingTimeoutException extends Exception {

	private static final long serialVersionUID = 5105605710850712121L;

	public RemotingTimeoutException() {
		super();
	}

	public RemotingTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

	public RemotingTimeoutException(String message) {
		super(message);
	}

	public RemotingTimeoutException(Throwable cause) {
		super(cause);
	}

}
