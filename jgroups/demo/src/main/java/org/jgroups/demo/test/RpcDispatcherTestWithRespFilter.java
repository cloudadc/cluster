package org.jgroups.demo.test;

import org.jgroups.Address;
import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.blocks.MethodCall;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.blocks.RpcDispatcher;
import org.jgroups.blocks.RspFilter;
import org.jgroups.demo.User;
import org.jgroups.util.RspList;
import org.jgroups.util.Util;

public class RpcDispatcherTestWithRespFilter extends TestBase implements IRPCInvokeMethod {
		
	private RpcDispatcher disp;
	private static Channel channel;
	private RspList rsp_list;


	public void test() throws Exception {
		
		System.out.println("'org.jgroups.blocks.RpcDispatcher' test with response filter");
		
		channel = new JChannel();
		disp = new RpcDispatcher(channel, this);
		channel.connect("RpcDispatcherTestGroup");
		
		System.out.println(" -> View Id: " + channel.getView().getViewId());
		System.out.println(" -> View Creater: " + channel.getView().getCreator());
		System.out.println(" -> View Coordinator: " + channel.getView().getMembers().get(0));
		System.out.println(" -> View Memembers: " + channel.getView().getMembers() + "\n");
		
		RequestOptions requestOptions = new RequestOptions(ResponseMode.GET_ALL, 5000, false, new RspFilter(){

			int num=0;

			public boolean isAcceptable(Object response, Address sender) {
				System.out.println("&& " + response);
				return true;

			}

			public boolean needMoreResponses() {
				return num < 2;
			}});
		
		for (int i = 0; i < 5; i++){
			Util.sleep(100);
			System.out.println("Invoke all members 'getUser' method");
			rsp_list = disp.callRemoteMethods(null
											, "getUser"
											, new Object[]{101, "RPC remote test", new User(1000, "Kylin Soong", "IT")}
											, new Class[]{int.class, String.class, User.class}
											, requestOptions);
			System.out.println("Responses:\n" + rsp_list);
		}
	}

	public static void main(String[] args) throws Exception {

		new RpcDispatcherTestWithRespFilter().test();
	}

	public User getUser(int id, String name, User user) {
		
		System.out.println("getUser method invoked, id: " + id + ", name: " + name + ", user: " + user);
		return new User(channel.getView().size(), "View ID: " + channel.getView().getViewId(), "View Memembers: " + channel.getView().getMembers());
	}

}
