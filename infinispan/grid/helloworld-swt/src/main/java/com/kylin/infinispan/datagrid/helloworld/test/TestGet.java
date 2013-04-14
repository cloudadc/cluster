package com.kylin.infinispan.datagrid.helloworld.test;

import java.util.Set;

import com.kylin.infinispan.datagrid.helloworld.CacheDelegate;
import com.kylin.infinispan.datagrid.helloworld.CacheDelegateImpl;

/**
 * 
 * mvn clean install dependency:copy-dependencies
 * 
 * java -cp target/dependency/*:target/grid-helloworld.jar -Djava.net.preferIPv4Stack=true com.kylin.infinispan.datagrid.helloworld.test.TestGet
 * 
 * @author kylin
 *
 */
public class TestGet {


	public static void main(String[] args) {
		CacheDelegate delegate = new CacheDelegateImpl();
		Set<String> keySet = delegate.getGenericCache().keySet();
		System.out.println("List All Exist Entities");
		for (String key : keySet){
			System.out.println("  " + key + " -> " + delegate.getGenericCache().get(key));
		}
	}

}
