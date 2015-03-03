package org.jgroups.demo.test.blocks;

import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.MembershipListener;
import org.jgroups.MessageListener;
import org.jgroups.blocks.MethodCall;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.blocks.RpcDispatcher;
import org.jgroups.util.NotifyingFuture;
import org.jgroups.util.RspList;
import org.jgroups.util.Util;

/**
 * 
 * @author kylin
 * 
 * Run
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar  -Djava.net.preferIPv4Stack=true com.kylin.jgroups.blocks.RpcDispatcherContentTestWithFuture -n node1
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar  -Djava.net.preferIPv4Stack=true com.kylin.jgroups.blocks.RpcDispatcherContentTestWithFuture -n node2
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar  -Djava.net.preferIPv4Stack=true com.kylin.jgroups.blocks.RpcDispatcherContentTestWithFuture -n node3
 *
 */
public class RpcDispatcherContentTestWithFuture {
	
	private RpcDispatcher disp;
	private Channel channel;
	private MessageListener messageListener;
	private MembershipListener membershipListener;
	private RpcMethods rpcMethods ;
	
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
		
		for(int i = 0 ; i < 10 ; i ++) {
			String viewId = null;
			if(null != channel.getView()) {
				viewId = channel.getView().getViewId().toString();
			}
			Content content = new Content(i, channel.getName(), viewId);
			MethodCall call = new MethodCall("updateContent", new Object[]{content}, new Class[]{Content.class});
			System.out.println("Call all group members updatecontent method: " + content);
			
			NotifyingFuture<RspList<Content>> future = disp.callRemoteMethodsWithFuture(null, call, requestOptions);
			
			Util.sleep(1000);
			
			System.out.println(channel.getName() + " Responses:");
			
			future.setListener(new MyFutureListener());
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
		 
		 new RpcDispatcherContentTestWithFuture().start(name);
	}

}
