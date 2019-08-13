package io.github.pikaq;

public enum RunningState {

	RUNNING, WAITING;

	public boolean isRunning() {
		return RUNNING.equals(this);
	}
}
