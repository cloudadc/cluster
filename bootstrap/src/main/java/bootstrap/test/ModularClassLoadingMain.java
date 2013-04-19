package bootstrap.test;

import org.jboss.modules.Main;

public class ModularClassLoadingMain {

	public static void main(String[] args) throws Throwable {
				
		System.setProperty("demo.home.dir", "/home/kylin/work/project/cluster/bootstrap/build/Demo");
		
		String[] array = new String[]{"-mp" 
									, "/home/kylin/work/project/cluster/bootstrap/build/Demo/modules"
//									, "-jaxpmodule"
//									, "javax.xml.jaxp-provider"
									, "bootstrap.infinispan"
									, "-c"
									, "infinispan-distribution.xml"};
		
		Main.main(array);
	}

}
