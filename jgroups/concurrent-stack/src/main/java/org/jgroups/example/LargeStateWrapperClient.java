package org.jgroups.example;

import org.jgroups.tests.LargeState;

public class LargeStateWrapperClient {

	public static void main(String[] args) {
		
		args = new String[]{"-props", "udp-stack.xml"};

		LargeState.main(args);
	}
}
