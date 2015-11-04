package org.jgroups.demo.test;

import org.jgroups.Address;
import org.jgroups.JChannel;

public class JChannelGetTest extends TestBase {
	
	public void test() throws Exception {

		System.out.println("JChannel getAddress(), getClusterName(), getView() Test");
		
		JChannel channel = new JChannel();
		channel.connect("ChatCluster");
		
		System.out.println("** getAddress(): " + channel.getAddress());
		System.out.println("** getClusterName(): " + channel.getClusterName());
		
		System.out.println("** getView(): " + channel.getView());
		System.out.println(" -> View Id: " + channel.getView().getViewId());
		System.out.println(" -> View Creater: " + channel.getView().getCreator());
		System.out.println(" -> View Coordinator: " + channel.getView().getMembers().get(0));
		System.out.println(" -> View Memembers: " + channel.getView().getMembers());


	}

	public static void main(String[] args) throws Exception {
		
		new JChannelGetTest().test();
	}

}
