package org.jgroups.demo.test;

import org.jgroups.Header;
import org.jgroups.protocols.FcHeader;

public class HeaderTest extends TestBase {
	
	public void test() throws Exception {

		System.out.println("'org.jgroups.Header' test");
		
		byte b = 2;
		Header header = new FcHeader(b);
		System.out.println(header);	
	}


	public static void main(String[] args) throws Exception{
		new HeaderTest().test();
	}

}
