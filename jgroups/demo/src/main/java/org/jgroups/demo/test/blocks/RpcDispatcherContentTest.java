package org.jgroups.demo.test.blocks;

import java.util.List;

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
 * RpcDispatcher is derived from MessageDispatcher. It allows a programmer to invoke remote methods in all (or single) cluster members and 
 * optionally wait for the return value(s). 
 * 
 * Build
 *   mvn clean dependency:copy-dependencies install
 * Run
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar  -Djava.net.preferIPv4Stack=true com.kylin.jgroups.blocks.RpcDispatcherContentTest -n node1
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar  -Djava.net.preferIPv4Stack=true com.kylin.jgroups.blocks.RpcDispatcherContentTest -n node2
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar  -Djava.net.preferIPv4Stack=true com.kylin.jgroups.blocks.RpcDispatcherContentTest -n node3
 *
 */
public class RpcDispatcherContentTest {
	
	
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
		
		RequestOptions requestOptions = new RequestOptions(ResponseMode.GET_ALL, 0);
		
		Util.sleep(1000);
		String viewId = null;
		if(null != channel.getView()) {
			viewId = channel.getView().getViewId().toString();
		}
		Content content = new Content(1, channel.getName(), viewId);
		MethodCall call = new MethodCall("updateContent", new Object[]{content}, new Class[]{Content.class});
		System.out.println("Call all group members updatecontent method: " + content);
		rsp_list = disp.callRemoteMethods(null, call, requestOptions);
		
		System.out.println("Responses:");
		List<Content> list = rsp_list.getResults();
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
		 
		 new RpcDispatcherContentTest().start(name);
	}

	
}
