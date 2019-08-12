package io.github.pikaq.remoting.protocol.command;

public enum RequestCode {

	CARRIER(0),
	
	HEART_BEAT_REQ(-1),
	HEART_BEAT_RSP(1),
	
	RPC_REQ(-2),
	RPC_RSP(2),

	;
	
	private RequestCode(int code) {
		this.code = code;
	}

	private int code;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isRequest() {
		return getCode() < 0;
	}

	public boolean isResponse() {
		return getCode() > 0;
	}

	public RequestCode toResponse() {
		String name = this.name();
		String rspName = name.substring(0, name.length() - 3).concat("RSP");
		return toEnum(rspName);
	}

	public static RequestCode toEnum(String name) {
		for (RequestCode command : values()) {
			if (command.name().equals(name)) {
				return command;
			}
		}
		return null;
	}

	public static RequestCode toEnum(int code) {
		for (RequestCode command : values()) {
			if (command.getCode() == code) {
				return command;
			}
		}
		return null;
	}
	
}
