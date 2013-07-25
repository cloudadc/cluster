package org.infinispan.grid.demo.test;

import org.infinispan.grid.demo.InfinispanConsole;
import org.infinispan.grid.demo.InfinispanTableImpl;

public class InfinispanTableImplTest {

	public static void main(String[] args) {
		InfinispanConsole console = new InfinispanConsole("infinispan-config.xml", true);
		InfinispanTableImpl impl = new InfinispanTableImpl("Test", "infinispan_icon_32px.gif", console, TestUtil.getDelegate());
		impl.start();
	}

}
