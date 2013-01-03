package com.kylin.infinispan.stu.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.infinispan.util.FileLookupFactory;

public class FileLookupFactoryTest {

	public static void main(String[] args) throws IOException {
		
		InputStream in = FileLookupFactory.newInstance().lookupFileStrict("jgroups.xml", Thread.currentThread().getContextClassLoader());
	
		char[] buffer = new char[4096];
		
		Reader reader = new InputStreamReader(in, "UTF-8");
		
		reader.read(buffer);
	
		System.out.println(buffer);


	}

}
