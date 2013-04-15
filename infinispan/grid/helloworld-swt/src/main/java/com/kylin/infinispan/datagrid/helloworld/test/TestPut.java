package com.kylin.infinispan.datagrid.helloworld.test;

import com.kylin.infinispan.datagrid.helloworld.CacheDelegate;
import com.kylin.infinispan.datagrid.helloworld.CacheDelegateImpl;

/**
 * 
 * mvn clean install dependency:copy-dependencies
 * 
 * java -cp target/dependency/*:target/grid-helloworld.jar -Djava.net.preferIPv4Stack=true com.kylin.infinispan.datagrid.helloworld.test.TestPut
 * 
 * @author kylin
 *
 */
public class TestPut {

	public static void main(String[] args) {
		
		CacheDelegate delegate = new CacheDelegateImpl("infinispan-distribution.xml");
		delegate.getGenericCache().put("key", "value");
		System.out.println("key -> value added");
	}

}
