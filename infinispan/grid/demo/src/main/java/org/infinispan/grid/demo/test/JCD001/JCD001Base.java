package org.infinispan.grid.demo.test.JCD001;

import java.io.File;

public class JCD001Base {

	protected static final String DEMO_HOME = "/home/kylin/work/project/cluster/bootstrap/build/Demo";

	static {
		System.setProperty("demo.home.dir", DEMO_HOME);
		System.setProperty("demo.conf.dir", DEMO_HOME + File.separator + "conf");
	}
}
