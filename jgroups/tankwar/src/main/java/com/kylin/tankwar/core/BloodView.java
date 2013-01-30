package com.kylin.tankwar.core;

import java.io.Serializable;

public class BloodView implements Serializable {

	private static final long serialVersionUID = 9079514259586228641L;
	
	private int step;
	
	private boolean isLive;

	public BloodView(int step, boolean isLive) {
		super();
		this.step = step;
		this.isLive = isLive;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	@Override
	public String toString() {
		return "BloodView [step=" + step + ", isLive=" + isLive + "]";
	}

}
