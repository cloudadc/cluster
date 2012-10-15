package com.kylin.infinispan.stu.querying;

import org.infinispan.Cache;

import com.kylin.infinispan.stu.InfinispanStuBase;

public class SearchTest extends InfinispanStuBase {

	public static void main(String[] args) {
		new SearchTest().test();
	}

	private void test() {
		
		Cache<String, Book> cache = getEmbeddedCacheManager().getCache("Test") ;
		
	}

}
