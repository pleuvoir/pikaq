package io.github.pikaq.protocol.serialization;

/**
 * 序列化异常
 * @author pleuvoir
 *
 */
public class SerializationException extends RuntimeException {

	private static final long serialVersionUID = 8919985937998358097L;

	public SerializationException() {
		super();
	}

	public SerializationException(String message, Throwable cause) {
		super(message, cause);
	}

	public SerializationException(String message) {
		super(message);
	}

	public SerializationException(Throwable cause) {
		super(cause);
	}

}
