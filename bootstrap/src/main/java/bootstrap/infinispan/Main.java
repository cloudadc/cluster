package bootstrap.infinispan;

import java.io.File;
import java.io.IOException;

import bootstrap.Bootstrap;

public class Main extends Bootstrap{

	public static void main(String[] args) throws IOException {
		for (int i = 0; i < args.length; i++){
			if (args[i].equals("-config") || args[i].equals("-c")){
				args[i+1] = System.getProperty("demo.conf.dir") + File.separator + args[i+1];
			}
		}
		
		com.kylin.infinispan.datagrid.helloworld.Main.main(args);
	}

}
