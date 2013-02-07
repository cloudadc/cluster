package com.kylin.infinispan.demo.test;

import com.kylin.infinispan.demo.InfinispanTableImpl;

public class InfinispanTableImplTest {

	public static void main(String[] args) {
		InfinispanTableImpl impl = new InfinispanTableImpl("Test", "infinispan_icon_32px.gif");
		impl.start();
	}

}
