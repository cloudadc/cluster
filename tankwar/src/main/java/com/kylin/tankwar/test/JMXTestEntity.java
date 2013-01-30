package com.kylin.tankwar.test;

import com.kylin.tankwar.jmx.annotations.MBean;
import com.kylin.tankwar.jmx.annotations.ManagedAttribute;
import com.kylin.tankwar.jmx.annotations.ManagedOperation;

@MBean(description = "JMXTestEntiry")
public class JMXTestEntity {

	@ManagedAttribute(description = "Entity message", name = "msg")
	private String msg;
	
	@ManagedAttribute(description = "Entity value", name = "value")
	private int value;

	public JMXTestEntity(String msg, int value) {
		super();
		this.msg = msg;
		this.value = value;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	@ManagedOperation(description = "myManagedOperation")
	public void myManagedOperation() {
		setMsg("This is my managed operation");
	}
}
