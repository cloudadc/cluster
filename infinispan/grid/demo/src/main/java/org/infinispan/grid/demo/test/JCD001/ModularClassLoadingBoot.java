package org.infinispan.grid.demo.test.JCD001;

import java.io.File;

import org.jboss.modules.Main;

public class ModularClassLoadingBoot extends JCD001Base{
	

	public static void main(String[] args) throws Throwable {
				
		String mp = DEMO_HOME + File.separator + "modules";
		
		String[] array = new String[]{"-mp" 
				, mp
//				, "-jaxpmodule"
//				, "javax.xml.jaxp-provider"
				, "bootstrap.infinispan"
				, "-c"
				, "infinispan-distribution.xml"};
		
		Main.main(array);
	}

}
