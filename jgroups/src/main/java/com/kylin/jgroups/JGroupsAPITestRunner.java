package com.kylin.jgroups;

import org.apache.log4j.Logger;

import com.kylin.jgroups.test.AddressTest;
import com.kylin.jgroups.test.EventTest;
import com.kylin.jgroups.test.HeaderTest;
import com.kylin.jgroups.test.JChannelGetTest;
import com.kylin.jgroups.test.JChannelTest;
import com.kylin.jgroups.test.MessageDispatcherTest;
import com.kylin.jgroups.test.MessageTest;
import com.kylin.jgroups.test.RpcDispatcherTest;
import com.kylin.jgroups.test.RpcDispatcherTestWithFuture;
import com.kylin.jgroups.test.RpcDispatcherTestWithRespFilter;
import com.kylin.jgroups.test.UtilTest;
import com.kylin.jgroups.test.ViewTest;

public class JGroupsAPITestRunner {
	
	private static final Logger logger = Logger.getLogger(JGroupsAPITestRunner.class);

	public static void main(String[] args) throws Exception {
		
		logger.info("----- JGroups API Test Start --------");

		new UtilTest().test();
		logger.info("END\n");
		
		new AddressTest().test();
		logger.info("END\n");
		
		new MessageTest().test();
		logger.info("END\n");
		
		new HeaderTest().test();
		logger.info("END\n");
		
		new EventTest().test();
		logger.info("END\n");
		
		new ViewTest().test();
		logger.info("END\n");
		
		new JChannelTest().test();
		logger.info("END\n");
		
		new JChannelGetTest().test();
		logger.info("END\n");
		
		new MessageDispatcherTest().test();
		logger.info("END\n");
		
		new RpcDispatcherTest().test();
		logger.info("END\n");
		
		new RpcDispatcherTestWithFuture().test();
		logger.info("END\n");
		
		new RpcDispatcherTestWithRespFilter().test();
		logger.info("END\n");
		
		logger.info("----- JGroups API Test End --------");
		

	}

}
