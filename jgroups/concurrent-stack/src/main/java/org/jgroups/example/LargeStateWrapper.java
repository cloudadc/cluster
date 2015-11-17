package org.jgroups.example;

import org.jgroups.tests.LargeState;

public class LargeStateWrapper {

	public static void main(String[] args) {
		
		args = new String[]{"-props", "udp-stack.xml", "-provider", "-size", "100000000", "-name", "LargeStateWrapper"};

		LargeState.main(args);
	}

}
