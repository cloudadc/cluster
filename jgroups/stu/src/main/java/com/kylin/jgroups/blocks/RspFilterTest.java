package com.kylin.jgroups.blocks;

import java.util.List;

import org.apache.log4j.Logger;
import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.MembershipListener;
import org.jgroups.MessageListener;
import org.jgroups.blocks.MethodCall;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.blocks.RpcDispatcher;
import org.jgroups.util.RspList;
import org.jgroups.util.Util;

public class RspFilterTest {
	
	private static final Logger logger = Logger.getLogger(RspFilterTest.class);
	
	private RpcDispatcher disp;
	private Channel channel;
	private MessageListener messageListener;
	private MembershipListener membershipListener;
	private RpcMethods rpcMethods ;
	private RspList<String> rsp_list;
	
	private void start(String name) throws Exception {
		
		channel = new JChannel();
		if(null != name) {
			channel.setName(name);
		}
		
		messageListener = new MyMessageListener();
		membershipListener = new MyMembershipListener();
		rpcMethods = new RpcMethods(channel);
		disp = new RpcDispatcher(channel, messageListener, membershipListener, rpcMethods);
		channel.connect("RspFilterTestGroup");
		
		Util.sleep(1000);
		String param = channel.getName();
		MethodCall call = new MethodCall("getNodeName", new Object[]{param}, new Class[]{String.class});
		logger.info("Call all members getNodeName()");
		RequestOptions requestOptions = new RequestOptions(ResponseMode.GET_ALL, 0, false, new MyRspFilter());		
		rsp_list = disp.callRemoteMethods(null, call, requestOptions);
		
		System.out.println("Responses:");
		List<String> list = rsp_list.getResults();
		for(Object obj : list) {
			System.out.println("  " + obj);
		}

	}

	public static void main(String[] args) throws Exception {
		
		String name = null;
		
		 for(int i=0; i < args.length; i++) {
			 
				if ("-n".equals(args[i]) ) {
					name = args[++i];
					continue ;
				}
				
		 }
		 
		new RspFilterTest().start(name);
	}

}
