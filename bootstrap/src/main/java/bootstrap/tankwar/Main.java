package bootstrap.tankwar;

import java.io.File;

import com.kylin.tankwar.TankWar;

import bootstrap.Bootstrap;

public class Main extends Bootstrap{

	public static void main(String[] args) {
		
		String name = null;
		boolean isGood = false ;
		
		for(int i=0; i < args.length; i++) {

			if (args[i].equals("-n")) {
				name = args[++i];
				continue;
			}
			
			if (args[i].equals("isGood")) {
				isGood = true ;
				continue;
			}

			help();
		}
		
		if(null == name) {
			System.out.println("You need prompt a unique name");
			help();
		}
		
		String jgroupsProps = System.getProperty("demo.conf.dir") + File.separator + "tankwar-udp.xml" ;
		
		new TankWar(jgroupsProps, name, isGood).doStart();
	}

	private static void help() {
		System.out.println("Run Application with [-n <name>] [isGood]");
		System.out.println("On Linux:");
		System.out.println("		./tankwar.sh -n <node name> isGood");
		System.out.println("On Windows:");
		System.out.println("		tankwar.bat -n <node name> isGood");
		Runtime.getRuntime().exit(0);
	}

}
