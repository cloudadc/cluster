package com.kylin.infinispan.helloworld.clustered.replication;

import java.io.IOException;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

import com.kylin.infinispan.helloworld.clustered.AbstractNode;

/**
 * How to Build?
 *   mvn clean install dependency:copy-dependencies
 * How to Run?
 *   java -cp target/infinispan-stu-helloworld-1.0.jar:target/dependency/* -Djava.net.preferIPv4Stack=true com.kylin.infinispan.helloworld.clustered.replication.ReplNode 1
 *   java -cp target/infinispan-stu-helloworld-1.0.jar:target/dependency/* -Djava.net.preferIPv4Stack=true com.kylin.infinispan.helloworld.clustered.replication.ReplNode 2
 *   java -cp target/infinispan-stu-helloworld-1.0.jar:target/dependency/* -Djava.net.preferIPv4Stack=true com.kylin.infinispan.helloworld.clustered.replication.ReplNode 3
 *   java -cp target/infinispan-stu-helloworld-1.0.jar:target/dependency/* -Djava.net.preferIPv4Stack=true com.kylin.infinispan.helloworld.clustered.replication.ReplNode 4
 * 
 * 
 * @author kylin
 *
 */
public class ReplNode extends AbstractNode {
	
	private static final String CACHE_NAME = "Infinispan-Replication-Demo";

	public ReplNode() throws IOException {
		super();
	}
	
	protected String getCacheName() {
		return CACHE_NAME;
	}

	protected EmbeddedCacheManager createCacheManager() throws IOException {
		return new DefaultCacheManager(GlobalConfigurationBuilder.defaultClusteredBuilder().transport().addProperty("configurationFile", "jgroups.xml").build(),
									   new ConfigurationBuilder().clustering().cacheMode(CacheMode.REPL_SYNC).build());
	}
	
	protected void run(String node) throws Exception{

		while(true) {
			System.out.println("Press any key add value to cache");
			System.in.read();
			cache.put("key-" + node, "Infinispan Replication Mode");
			System.out.println("Add  key-" + node + " -> " + cache.get("key-" + node) + "  to " + cache.getName() + "/" + cache.getVersion());
			System.out.println();
			Thread.currentThread();
			Thread.sleep(2000);
		}
	}

	public static void main(String[] args) throws Exception {
		if(args.length <= 0) {
			System.out.println("Please Run with a node id parameter:");
			System.out.println("    com.kylin.infinispan.helloworld.clustered.replication.ReplNode X");
			Runtime.getRuntime().exit(0);
		}
		new ReplNode().run(args[0]);
	}

	

}
