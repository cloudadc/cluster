package org.jgroups.demo.test;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.blocks.MethodCall;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.blocks.RpcDispatcher;
import org.jgroups.demo.User;
import org.jgroups.util.FutureListener;
import org.jgroups.util.NotifyingFuture;
import org.jgroups.util.RspList;
import org.jgroups.util.Util;

public class RpcDispatcherTestWithFuture extends TestBase implements IRPCInvokeMethod {
		
	private RpcDispatcher disp;
	private static Channel channel;
	private RspList rsp_list;


	public void test() throws Exception {
		
		System.out.println("'org.jgroups.blocks.RpcDispatcher' test with future");
		
		channel = new JChannel();
		disp = new RpcDispatcher(channel, this);
		channel.connect("RpcDispatcherTestGroup");
		
		System.out.println(" -> View Id: " + channel.getView().getViewId());
		System.out.println(" -> View Creater: " + channel.getView().getCreator());
		System.out.println(" -> View Coordinator: " + channel.getView().getMembers().get(0));
		System.out.println(" -> View Memembers: " + channel.getView().getMembers() + "\n");
		
		MethodCall method_call = new MethodCall("getUser", new Object[]{101, "RPC remote test", new User(1000, "Kylin Soong", "IT")}, new Class[]{int.class, String.class, User.class});
		
//		String methodName = RpcDispatcherTest.class.getMethod("getUser", new Class[] {int.class, String.class, User.class}).getName();
//		System.out.println(methodName);
		
		for (int i = 0; i < 5; i++){
			Util.sleep(100);
			System.out.println("Invoke all members 'getUser' method");
			NotifyingFuture<RspList> future = disp.callRemoteMethodWithFuture(channel.getAddress(), method_call, new RequestOptions(ResponseMode.GET_ALL, 5000));
			future.setListener(new FutureListener(){

				public void futureDone(Future future) {
					try {
						System.out.println("result is " + future.get());
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}});

		}
	}

	public static void main(String[] args) throws Exception {

		new RpcDispatcherTestWithFuture().test();
	}
	
	Random r = new Random();

	public User getUser(int id, String name, User user) {
		
		System.out.println("getUser method invoked, id: " + id + ", name: " + name + ", user: " + user);
		int sleep = r.nextInt(3000);
		Util.sleep(sleep);
		System.out.println("Sleep " + sleep + " milliseconds");
		return new User(sleep, "View ID: " + channel.getView().getViewId(), "View Memembers: " + channel.getView().getMembers());
	}

}
