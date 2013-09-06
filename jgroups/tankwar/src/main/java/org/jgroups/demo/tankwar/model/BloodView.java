package org.jgroups.demo.tankwar.model;

import java.io.Serializable;

public class BloodView implements Serializable {

	private static final long serialVersionUID = 9079514259586228641L;
	
	private int step;
	
	private boolean isLive;
	
	private int count;

	public BloodView(int step, boolean isLive, int count) {
		super();
		this.step = step;
		this.isLive = isLive;
		this.count = count;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "BloodView [step=" + step + ", isLive=" + isLive + ", count="
				+ count + "]";
	}

}
