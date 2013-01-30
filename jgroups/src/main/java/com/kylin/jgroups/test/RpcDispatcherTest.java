package com.kylin.jgroups.test;

import org.apache.log4j.Logger;
import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.blocks.RpcDispatcher;
import org.jgroups.util.RspList;
import org.jgroups.util.Util;

import com.kylin.jgroups.TestBase;
import com.kylin.jgroups.User;

public class RpcDispatcherTest extends TestBase implements IRPCInvokeMethod {
	
	private static final Logger logger = Logger.getLogger(RpcDispatcherTest.class);
	
	private RpcDispatcher disp;
	private static Channel channel;
	private RspList rsp_list;


	public void test() throws Exception {
		
		logger.info("'org.jgroups.blocks.RpcDispatcher' test");
		
		channel = new JChannel();
		disp = new RpcDispatcher(channel, this);
		channel.connect("RpcDispatcherTestGroup");
		
		logger.info(" -> View Id: " + channel.getView().getViewId());
		logger.info(" -> View Creater: " + channel.getView().getCreator());
		logger.info(" -> View Coordinator: " + channel.getView().getMembers().get(0));
		logger.info(" -> View Memembers: " + channel.getView().getMembers() + "\n");
		
		for (int i = 0; i < 5; i++){
			Util.sleep(100);
			System.out.println("Invoke all members 'getUser' method");
			rsp_list = disp.callRemoteMethods(null
											, "getUser"
											, new Object[]{101, "RPC remote test", new User(1000, "Kylin Soong", "IT")}
											, new Class[]{int.class, String.class, User.class}
											, new RequestOptions(ResponseMode.GET_ALL, 5000));
			System.out.println("Responses:\n" + rsp_list);
		}
	}

	public static void main(String[] args) throws Exception {

		new RpcDispatcherTest().test();
	}

	public User getUser(int id, String name, User user) {
		
		logger.info("getUser method invoked, id: " + id + ", name: " + name + ", user: " + user);
		return new User(channel.getView().size(), "View ID: " + channel.getView().getViewId(), "View Memembers: " + channel.getView().getMembers());
	}

}
