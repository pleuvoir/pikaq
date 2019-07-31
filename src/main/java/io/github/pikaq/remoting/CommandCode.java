package io.github.pikaq.remoting;

public enum CommandCode {

	
	HEART_BEAT_REQ(-1),
	HEART_BEAT_RSP(1),
	
	RPC_REQ(-2),
	RPC_RSP(2),

	;
	
	private CommandCode(int code) {
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

	public CommandCode toResponse() {
		String name = this.name();
		String rspName = name.substring(0, name.length() - 3).concat("RSP");
		return toEnum(rspName);
	}

	public static CommandCode toEnum(String name) {
		for (CommandCode command : values()) {
			if (command.name().equals(name)) {
				return command;
			}
		}
		return null;
	}

	public static CommandCode toEnum(int code) {
		for (CommandCode command : values()) {
			if (command.getCode() == code) {
				return command;
			}
		}
		return null;
	}

}
