package bootstrap.jgroups.state;

import java.io.File;

import com.kylin.jgroups.performance.LargeState;

import bootstrap.Bootstrap;

public class Main extends Bootstrap{

	public static void main(String[] args) {
		
		boolean provider = false, provider_fails = false, requester_fails = false;
		int size = 1024 * 1024;
		String props = null;
		long delay = 0;
		String name = null;

		for (int i = 0; i < args.length; i++) {
			if ("-help".equals(args[i])) {
				help();
				return;
			}
			if ("-provider".equals(args[i])) {
				provider = true;
				continue;
			}
			if ("-provider_fails".equals(args[i])) {
				provider_fails = true;
				continue;
			}
			if ("-requester_fails".equals(args[i])) {
				requester_fails = true;
				continue;
			}
			if ("-size".equals(args[i])) {
				size = Integer.parseInt(args[++i]);
				continue;
			}
			if ("-props".equals(args[i])) {
				props = args[++i];
				continue;
			}
			if ("-delay".equals(args[i])) {
				delay = Long.parseLong(args[++i]);
				continue;
			}
			if ("-name".equals(args[i])) {
				name = args[++i];
				continue;
			}
			help();
			return;
		}
		
		String jgroupsProps = System.getProperty("demo.conf.dir") + File.separator + props ;
		if(!new File(jgroupsProps).exists()) {
			throw new IllegalArgumentException("-props " + props + " doesn't exist in " + System.getProperty("demo.conf.dir"));
		}
		
		try {
            new LargeState().start(provider, size, jgroupsProps, provider_fails, requester_fails, delay, name);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
		
	}
	
	private static void help() {
		System.out.println("Run Application with [-help] [-size <size of state in bytes] [-provider] [-name name] [-props <properties>] [-provider_fails] [-requester_fails] [-delay <ms>]");
		System.out.println("On Linux:");
		System.out.println("		./largestate.sh [-help] [-size <size of state in bytes] [-provider] [-name name] [-props <properties>] [-provider_fails] [-requester_fails] [-delay <ms>]");
		System.out.println("On Windows:");
		System.out.println("		largestate.bat [-help] [-size <size of state in bytes] [-provider] [-name name] [-props <properties>] [-provider_fails] [-requester_fails] [-delay <ms>]");
		Runtime.getRuntime().exit(0);
	}
}
