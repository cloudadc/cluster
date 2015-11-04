package org.jgroups.demo.test;

import org.jgroups.JChannel;
import org.jgroups.View;

public class ViewTest extends TestBase {
	

	public void test() throws Exception {
		
		System.out.println("'org.jgroups.View' test.");
				
		viewchecker(15);
		viewchecker(10);
		viewchecker(5);
		
		Thread.sleep(1000 * 15);
	}

	private void viewchecker(int time) throws Exception {

		JChannel channel = new JChannel();
		channel.connect("TestCluster");
		View view = channel.getView();
		System.out.println("View ID: " + view.getViewId());
		System.out.println("View creater: " + view.getCreator());
		System.out.println("View coordinator: " + view.getMembers().get(0));
		
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
