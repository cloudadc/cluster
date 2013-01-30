package com.kylin.jgroups.test;

import org.apache.log4j.Logger;
import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.blocks.RequestHandler;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.util.RspList;
import org.jgroups.util.Util;

import com.kylin.jgroups.TestBase;


public class MessageDispatcherTest extends TestBase implements RequestHandler {
	
	private static final Logger logger = Logger.getLogger(MessageDispatcherTest.class);
	
	private Channel channel;
	private MessageDispatcher disp;
	private RspList rsp_list;
	private String props; 


	public Object handle(Message msg) throws Exception {
		System.out.println("** Message: " + msg + ", message content: [" + new String(msg.getBuffer()) + "]");
		return "Success";
	}
	
	public static void main(String[] args) throws Exception {
		
		new MessageDispatcherTest().test();
	}

	public void test() throws Exception {
		
		logger.info("'org.jgroups.blocks.MessageDispatcher' test");
		
		channel = new JChannel(props);
		disp = new MessageDispatcher(channel, null, null, this);
		channel.connect("MessageDispatcherTestGroup");
		
		logger.info(" -> View Id: " + channel.getView().getViewId());
		logger.info(" -> View Creater: " + channel.getView().getCreator());
		logger.info(" -> View Coordinator: " + channel.getView().getMembers().get(0));
		logger.info(" -> View Memembers: " + channel.getView().getMembers() + "\n");
		
		for (int i = 0; i < 5; i++) {
			Util.sleep(100);
			System.out.println("Casting message #" + i);
			Message msg = new Message(null, null,new String("Kylin Soong JGroups Study Case, Number #" + i));
			rsp_list = disp.castMessage(null, msg, new RequestOptions(ResponseMode.GET_ALL, 0));
			System.out.println("Responses:\n" + rsp_list);
		}
//		channel.close();
//		disp.stop();

	}

}
