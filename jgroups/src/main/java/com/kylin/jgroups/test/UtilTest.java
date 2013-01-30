package com.kylin.jgroups.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.jgroups.util.Util;

import com.kylin.jgroups.TestBase;

public class UtilTest extends TestBase{
	
	private static final Logger logger = Logger.getLogger(UtilTest.class);

	public static void main(String[] args) throws Exception {
		new UtilTest().test();
	}

	public void test() throws Exception {
		
		logger.info("'org.jgroups.util.Util' test");
		
		objectToByteBufferTest();
		
		objectToStreamTest();
		
	}

	private void objectToStreamTest() throws Exception {
	
		String filename = "utiltest.out";
		
		DataOutputStream outputStream =  new DataOutputStream(new FileOutputStream(new File(filename)));
		Util.objectToStream(user, outputStream);
		outputStream.close();
		logger.info("convert " + user + " to " + filename +" file");
		
		DataInputStream inputStream = new DataInputStream(new FileInputStream(new File(filename)));
		Object obj = Util.objectFromStream(inputStream);
		inputStream.close();
		logger.info("convert " + filename + " to Object" + obj);
		
		logger.info("remove " + filename + ": " + new File(filename).delete());
	}

	private void objectToByteBufferTest() throws Exception {
		
		byte[] bytes = Util.objectToByteBuffer(user);
		logger.info("convert " + user + " to ByteBuffer: " + bytes.length);
		
		Object obj = Util.objectFromByteBuffer(bytes);
		logger.info("convert Bytebuffer to Object" + obj);
	}

}
