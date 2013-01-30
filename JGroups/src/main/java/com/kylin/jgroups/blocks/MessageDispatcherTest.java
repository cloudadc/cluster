package com.kylin.jgroups.blocks;

import java.util.List;

import org.apache.log4j.Logger;
import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.MembershipListener;
import org.jgroups.Message;
import org.jgroups.MessageListener;
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.blocks.RequestHandler;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.util.RspList;
import org.jgroups.util.Util;

/**
 * MessageDispatcher provides blocking (and non-blocking) request sending and
 * response correlation. It offers synchronous (as well as asynchronous) message
 * sending with request-response correlation,
 * 
 * MessageDispatcher deals with sending message requests and correlating message
 * responses
 * 
 * java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar  -Djava.net.preferIPv4Stack=true com.kylin.jgroups.blocks.MessageDispatcherTest -n node1
 * java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar  -Djava.net.preferIPv4Stack=true com.kylin.jgroups.blocks.MessageDispatcherTest -n node2
 * java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar  -Djava.net.preferIPv4Stack=true com.kylin.jgroups.blocks.MessageDispatcherTest -n node3
 * 
 */
public class MessageDispatcherTest {
	
	private static final Logger logger = Logger.getLogger(MessageDispatcherTest.class);

	private Channel channel;
	private RequestHandler handler;
	private MessageListener messageListener;
	private MembershipListener membershipListener;
	private MessageDispatcher disp;
	
	private RspList rsp_list;
	
	private String props;
	
	public void start(String name) throws Exception {
		
		channel = new JChannel(props);
		if(null != name) {
			channel.setName(name);
		}
		handler = new MyRequestHandler(channel);
		messageListener = new MyMessageListener();
		membershipListener = new MyMembershipListener();
		
		disp = new MessageDispatcher(channel, messageListener, membershipListener, handler);
		channel.connect("MessageDispatcherTestGroup");
		
		for(int i = 0 ; i < 10 ; i ++) {
			Util.sleep(100);
			logger.info("Casting message #" + i);
			Message message = new Message(null, null, new String("Number #" + i));
			rsp_list = disp.castMessage(null, message, new RequestOptions().setMode(ResponseMode.GET_ALL).setTimeout(0));
			
			logger.info(channel.getName() + " Responses:");
			
			List list = rsp_list.getResults();
			for(Object obj : list) {
				System.out.println("  " + obj);
			}
			System.out.println();
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

		new MessageDispatcherTest().start(name);
	}

}
