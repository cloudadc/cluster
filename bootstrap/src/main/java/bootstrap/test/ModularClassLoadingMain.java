package bootstrap.test;

import org.jboss.modules.Main;

public class ModularClassLoadingMain {

	public static void main(String[] args) throws Throwable {
		
		System.setProperty("demo.home.dir", "/home/kylin/work/project/TankWar/bootstrap/build/Demo");
		
		String[] array = new String[]{"-mp" 
									, "/home/kylin/work/project/TankWar/bootstrap/build/Demo/modules"
									, "bootstrap"};
		
		Main.main(array);
	}

}
