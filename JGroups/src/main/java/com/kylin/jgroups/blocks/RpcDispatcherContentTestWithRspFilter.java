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

/**
 * Interface defining when a group request is done. This allows for termination of a group request based on logic implemented by the caller
 * 
 * isAcceptable Determines whether a response from a given sender should be added to the response list of the request
 * 
 * whether more responses are needed
 *
 */
public class RpcDispatcherContentTestWithRspFilter {

private static final Logger logger = Logger.getLogger(RpcDispatcherContentTestWithRspFilter.class);
	
	private RpcDispatcher disp;
	private Channel channel;
	private MessageListener messageListener;
	private MembershipListener membershipListener;
	private RpcMethods rpcMethods ;
	private RspList<Content> rsp_list;
	
	private String props;
	
	private void start(String name) throws Exception {
		
		channel = new JChannel(props);
		if(null != name) {
			channel.setName(name);
		}
		
		messageListener = new MyMessageListener();
		membershipListener = new MyMembershipListener();
		rpcMethods = new RpcMethods(channel);
		disp = new RpcDispatcher(channel, messageListener, membershipListener, rpcMethods);
		channel.connect("RpcDispatcherContentTestGroup");
		
		RequestOptions requestOptions = new RequestOptions(ResponseMode.GET_ALL, 0, false, new MyRspFilter());
		
		for(int i = 0 ; i < 10 ; i ++) {
			Util.sleep(1000);
			String viewId = null;
			if(null != channel.getView()) {
				viewId = channel.getView().getViewId().toString();
			}
			Content content = new Content(i, channel.getName(), viewId);
			MethodCall call = new MethodCall("updateContent", new Object[]{content}, new Class[]{Content.class});
			logger.info("Call all group members updatecontent method: " + content);
			rsp_list = disp.callRemoteMethods(null, call, requestOptions);
			
			showRspList(rsp_list);
		}

	}
	
	private void showRspList(RspList<Content> rsp_list) {
		
		logger.info(channel.getName() + " Responses:");

		List<Content> list = rsp_list.getResults();
		for(Object obj : list) {
			System.out.println("  " + obj);
		}
		System.out.println();
	}

	public static void main(String[] args) throws Exception {
		

		String name = null;
		
		 for(int i=0; i < args.length; i++) {
			 
				if ("-n".equals(args[i]) ) {
					name = args[++i];
					continue ;
				}
				
		 }
		 
		 new RpcDispatcherContentTestWithRspFilter().start(name);
	}

}
