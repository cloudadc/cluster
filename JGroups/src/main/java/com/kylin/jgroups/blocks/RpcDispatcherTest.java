package com.kylin.jgroups.blocks;

import org.apache.log4j.Logger;
import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.MembershipListener;
import org.jgroups.MessageListener;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.blocks.RpcDispatcher;
import org.jgroups.util.RspList;


/**
 * RpcDispatcher is derived from MessageDispatcher. It allows a programmer to invoke remote methods in all (or single) cluster members and 
 * optionally wait for the return value(s). 
 *
 */
public class RpcDispatcherTest {
	
	private static final Logger logger = Logger.getLogger(RpcDispatcherTest.class);
	
	private RpcDispatcher disp;
	private static Channel channel;
	private MessageListener messageListener;
	private MembershipListener membershipListener;
	private RspList rsp_list;
	
	private String props;
	
	private void start(String name) throws Exception {
		
		channel = new JChannel(props);
		if(null != name) {
			channel.setName(name);
		}
		
		messageListener = new MyMessageListener();
		membershipListener = new MyMembershipListener();
		disp = new RpcDispatcher();
		
		RequestOptions requestOptions = new RequestOptions(ResponseMode.GET_ALL, 0);
		
		

	}
	
	public static void main(String[] args) throws Exception {
		

		String name = null;
		
		 for(int i=0; i < args.length; i++) {
			 
				if ("-n".equals(args[i]) ) {
					name = args[++i];
					continue ;
				}
				
		 }
		 
		 new RpcDispatcherTest().start(name);
	}

	
}
