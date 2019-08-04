package io.github.pikaq.remoting;

public enum RunningState {

	RUNNING, WAITING;

	public boolean isRunning() {
		return RUNNING.equals(this);
	}
}
