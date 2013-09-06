package bootstrap.tankwar;

import java.io.File;

import org.jgroups.demo.tankwar.TankWar;


import bootstrap.Bootstrap;

public class Main extends Bootstrap{

	public static void main(String[] args) {
		
		String name = null;
		boolean isGood = false ;
		String props = null;
		
		for(int i=0; i < args.length; i++) {

			if (args[i].equals("-n")) {
				name = args[++i];
				continue;
			}
			
			if (args[i].equals("-p") || args[i].equals("-props")) {
				props = args[++i];
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
		
		String jgroupsProps = null ;
		if(null == props) {
			jgroupsProps = System.getProperty("demo.conf.dir") + File.separator + "tankwar-udp.xml" ;
		} else {
			jgroupsProps = System.getProperty("demo.conf.dir") + File.separator + props ;
		}
		
		new TankWar(jgroupsProps, name, isGood).doStart();
	}

	private static void help() {
		System.out.println("Run Application with [-n <name>] [-p <config>] [isGood]");
		System.out.println("On Linux:");
		System.out.println("		./tankwar.sh -n <node name>  -p <config> isGood");
		System.out.println("On Windows:");
		System.out.println("		tankwar.bat -n <node name> -p <config> isGood");
		Runtime.getRuntime().exit(0);
	}

}
