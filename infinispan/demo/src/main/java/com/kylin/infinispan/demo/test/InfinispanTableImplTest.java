package com.kylin.infinispan.demo.test;

import com.kylin.infinispan.demo.InfinispanConsole;
import com.kylin.infinispan.demo.InfinispanTableImpl;

public class InfinispanTableImplTest {

	public static void main(String[] args) {
		InfinispanConsole console = new InfinispanConsole("infinispan-config.xml");
		InfinispanTableImpl impl = new InfinispanTableImpl("Test", "infinispan_icon_32px.gif", console, TestUtil.getDelegate());
		impl.start();
	}

}
