package io.github.pikaq.remoting.protocol;

import java.util.Map;

import com.google.common.collect.Maps;

public class RemoteCommand {

	private Command cmd;
	private Map<String, Object> attachs = Maps.newHashMap();

	public static RemoteCommand createHeartbeatReq() {
		RemoteCommand remoteCommand = new RemoteCommand();
		remoteCommand.setCmd(Command.HEART_BEAT_REQ);
		return remoteCommand;
	}

	public static RemoteCommand createHeartbeatRsp() {
		RemoteCommand remoteCommand = new RemoteCommand();
		remoteCommand.setCmd(Command.HEART_BEAT_RSP);
		return remoteCommand;
	}

	public Command getCmd() {
		return cmd;
	}

	public void setCmd(Command cmd) {
		this.cmd = cmd;
	}

	public Map<String, Object> getAttachs() {
		return attachs;
	}

	public void setAttachs(Map<String, Object> attachs) {
		this.attachs = attachs;
	}

	public static enum Command {

		// request
		HEART_BEAT_REQ(-1),

		// response
		HEART_BEAT_RSP(1)

		;
		private Command(int code) {
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

		public Command toResponse() {
			String name = this.name();
			String rspName = name.substring(0, name.length() - 3).concat("RSP");
			return toEnum(rspName);
		}

		public static Command toEnum(String name) {
			for (Command command : values()) {
				if (command.name().equals(name)) {
					return command;
				}
			}
			return null;
		}

		public static Command toEnum(int code) {
			for (Command command : values()) {
				if (command.getCode() == code) {
					return command;
				}
			}
			return null;
		}
	}

}
