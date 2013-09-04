package com.kylin.cluster.tools.h2;

import org.h2.tools.Server;

public class ToolsWrapper {


	public static void main(String[] args) {
		
		try {
			// start h2 in memory database
			Server server = Server.createWebServer(args);
			server.start();
			System.out.println("Start H2... " + server.getStatus());
		} catch (Throwable t) {
			throw new RuntimeException("Could not start H2 server", t);
		}
	}

}
