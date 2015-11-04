package org.jgroups.demo.tankwar;

import org.jgroups.demo.tankwar.core.MainFrame;
import org.jgroups.demo.tankwar.jgroups.AsychCommunication;


/**
 * 
 * mvn clean install dependency:copy-dependencies
 * 
 * java -cp ./target/jgroups-demo-tankwar.jar:./target/dependency/* -Djava.net.preferIPv4Stack=true org.jgroups.demo.tankwar.TankWar -p tankwar-udp.xml -n node1 isGood
 * java -cp ./target/jgroups-demo-tankwar.jar:./target/dependency/* -Djava.net.preferIPv4Stack=true org.jgroups.demo.tankwar.TankWar -p tankwar-udp.xml -n node2
 * 
 * @author kylin
 *
 */
public class TankWar {
		
	private String jgroupsProps ;
	
	private String name ;
	
	private boolean isGood ;
	
	public TankWar(String jgroupsProps, String name, boolean isGood) {
		this.jgroupsProps = jgroupsProps;
		this.name = name; 
		this.isGood = isGood;
	}
	
	public void doStart() {
		
		System.out.println("JGroups TankWar Demo doStart, [jgroupsProps=" + jgroupsProps + ", name=" + name + ", isGood=" + isGood + "]");
		
		// Current use asynchronous Communication
		AsychCommunication comm = new AsychCommunication(jgroupsProps, name);
		
		new MainFrame(comm, isGood);
	}

	public static void main(String[] args) {

		String props = "udp.xml";
		String name = null;
		boolean isGood = false ;
		
		for(int i=0; i < args.length; i++) {

			if (args[i].equals("-p")) {
				props = args[++i];
				continue;
			}

			if (args[i].equals("-n") || args[i].equals("-name")) {
				name = args[++i];
				continue;
			}
			
			if (args[i].equals("good")) {
				isGood = true ;
				continue;
			}

			System.out.println("Run Application with [-n <name>] [good]");
			System.exit(1);
		}
		
		AsychCommunication comm = new AsychCommunication(props, name);
		
		MainFrame mainFrame = new MainFrame(comm, isGood);
	}


}
