package com.kylin.ejb.remote.client;


public class JBossCacheService2NodesReplClient extends JBossCacheService2NodesClient {

	public JBossCacheService2NodesReplClient(String ipNode1, String ipNode2) throws Exception {
		super(ipNode1, ipNode2);
		serviceNode1.start();
		serviceNode2.start();
	}

	public void test() throws Exception {
		
		while(true) {
			for(int i = 1 ; i <= 1000 ; i ++) {
				serviceNode1.put("/a/b/c/" + i, 100000);
			}
//			stop(1000);
		}
		
	}

	public static void main(String[] args) throws Exception {
		new JBossCacheService2NodesReplClient("10.66.192.231","10.66.192.48").test();
	}

}
