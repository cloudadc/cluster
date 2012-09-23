package com.kylin.jgroups.test;

import org.apache.log4j.Logger;
import org.jgroups.Address;
import org.jgroups.JChannel;

import com.kylin.jgroups.TestBase;

public class JChannelGetTest extends TestBase {

	private static final Logger logger = Logger.getLogger(JChannelGetTest.class);
	
	public void test() throws Exception {

		logger.info("JChannel getAddress(), getClusterName(), getView() Test");
		
		JChannel channel = new JChannel();
		channel.connect("ChatCluster");
		
		logger.info("** getAddress(): " + channel.getAddress());
		logger.info("** getClusterName(): " + channel.getClusterName());
		
		logger.info("** getView(): " + channel.getView());
		logger.info(" -> View Id: " + channel.getView().getViewId());
		logger.info(" -> View Creater: " + channel.getView().getCreator());
		logger.info(" -> View Coordinator: " + channel.getView().getMembers().get(0));
		logger.info(" -> View Memembers: " + channel.getView().getMembers());
//		for( Address addr : channel.getView().getMembers()) {
//			logger.info("    className: " + addr.getClass() + ", value: " + addr);
//		}
	}

	public static void main(String[] args) throws Exception {
		
		new JChannelGetTest().test();
	}

}
