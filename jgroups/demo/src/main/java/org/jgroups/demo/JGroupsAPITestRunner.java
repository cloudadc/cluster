package org.jgroups.demo;

import org.jgroups.demo.test.AddressTest;
import org.jgroups.demo.test.EventTest;
import org.jgroups.demo.test.HeaderTest;
import org.jgroups.demo.test.JChannelGetTest;
import org.jgroups.demo.test.JChannelTest;
import org.jgroups.demo.test.MessageDispatcherTest;
import org.jgroups.demo.test.MessageTest;
import org.jgroups.demo.test.RpcDispatcherTest;
import org.jgroups.demo.test.RpcDispatcherTestWithFuture;
import org.jgroups.demo.test.RpcDispatcherTestWithRespFilter;
import org.jgroups.demo.test.UtilTest;
import org.jgroups.demo.test.ViewTest;

public class JGroupsAPITestRunner {
	

	public static void main(String[] args) throws Exception {
		

		new UtilTest().test();
		
		new AddressTest().test();
		
		new MessageTest().test();
		
		new HeaderTest().test();
		
		new EventTest().test();
		
		new ViewTest().test();
		
		new JChannelTest().test();
		
		new JChannelGetTest().test();
		
		new MessageDispatcherTest().test();
		
		new RpcDispatcherTest().test();
		
		new RpcDispatcherTestWithFuture().test();
		
		new RpcDispatcherTestWithRespFilter().test();

	}

}
