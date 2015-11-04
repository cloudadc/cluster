package org.jgroups.demo.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.jgroups.util.Util;

public class UtilTest extends TestBase{
	

	public static void main(String[] args) throws Exception {
		new UtilTest().test();
	}

	public void test() throws Exception {
		
		System.out.println("'org.jgroups.util.Util' test");
		
		objectToByteBufferTest();
		
		objectToStreamTest();
		
	}

	private void objectToStreamTest() throws Exception {
	
		String filename = "utiltest.out";
		
		DataOutputStream outputStream =  new DataOutputStream(new FileOutputStream(new File(filename)));
		Util.objectToStream(user, outputStream);
		outputStream.close();
		System.out.println("convert " + user + " to " + filename +" file");
		
		DataInputStream inputStream = new DataInputStream(new FileInputStream(new File(filename)));
		Object obj = Util.objectFromStream(inputStream);
		inputStream.close();
		System.out.println("convert " + filename + " to Object" + obj);
		
		System.out.println("remove " + filename + ": " + new File(filename).delete());
	}

	private void objectToByteBufferTest() throws Exception {
		
		byte[] bytes = Util.objectToByteBuffer(user);
		System.out.println("convert " + user + " to ByteBuffer: " + bytes.length);
		
		Object obj = Util.objectFromByteBuffer(bytes);
		System.out.println("convert Bytebuffer to Object" + obj);
	}

}
