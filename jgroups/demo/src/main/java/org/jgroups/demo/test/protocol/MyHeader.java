package org.jgroups.demo.test.protocol;

import java.io.DataInput;
import java.io.DataOutput;

import org.jgroups.Global;
import org.jgroups.Header;

public class MyHeader extends Header {

	private int counter = 0;
	
	public MyHeader() {
		
	}
	
	public MyHeader(int counter) {
		this.counter = counter;
	}

	public void writeTo(DataOutput out) throws Exception {
		out.writeInt(counter);
	}

	public void readFrom(DataInput in) throws Exception {
		counter = in.readInt();
	}

	public int size() {
		return Global.INT_SIZE;
	}

	public String toString() {
		return "MyHeader [counter=" + counter + "]";
	}

}
