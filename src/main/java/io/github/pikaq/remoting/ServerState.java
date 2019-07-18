package io.github.pikaq.remoting;

public enum ServerState {

	RUNNING, // 运行态
	WAITING, // 等待运行

	;

	public boolean isRunning() {
		return RUNNING.equals(this);
	}
}
