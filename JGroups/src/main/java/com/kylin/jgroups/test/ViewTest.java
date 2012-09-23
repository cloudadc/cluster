package com.kylin.jgroups.test;

import org.apache.log4j.Logger;
import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.View;

import com.kylin.jgroups.TestBase;

public class ViewTest extends TestBase {
	
	private static final Logger logger = Logger.getLogger(ViewTest.class);

	public void test() throws Exception {
		
		logger.info("'org.jgroups.View' test.");
				
		viewchecker(15);
		viewchecker(10);
		viewchecker(5);
		
		Thread.sleep(1000 * 15);
	}

	private void viewchecker(int time) throws Exception {

		JChannel channel = new JChannel();
		channel.connect("TestCluster");
		View view = channel.getView();
		logger.info("View ID: " + view.getViewId());
		logger.info("View creater: " + view.getCreator());
		logger.info("View coordinator: " + view.getMembers().get(0));
		
		new Thread(new ChannelCloseThread(channel, 1000 * time)).start();
	}
	
	private class ChannelCloseThread implements Runnable {
		
		private JChannel channel;

		private int time;
		
		public ChannelCloseThread(JChannel channel, int time) {
			super();
			this.channel = channel;
			this.time = time;
		}

		public void run() {
			
			try {
				Thread.sleep(time);
				channel.close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	public static void main(String[] args) throws Exception {

		new ViewTest().test();
	}

}
