package org.jgroups.demo.test.performance;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.management.MBeanServer;

import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.jmx.JmxConfigurator;
import org.jgroups.util.Util;

/**
 * Tests transfer of large states. Start first instance with -provider flag and -size flag (default = 1MB).
 * The start second instance without these flags: it should acquire the state from the first instance. Possibly
 * tracing should be turned on for FRAG to see the fragmentation taking place, e.g.:
 * <pre>
 * trace1=FRAG DEBUG STDOUT
 * </pre><br>
 * Note that because fragmentation might generate a lot of small fragments at basically the same time (e.g. size1MB,
 * FRAG.frag-size=4096 generates a lot of fragments), the send buffer of the unicast socket in UDP might be overloaded,
 * causing it to drop some packets (default size is 8096 bytes). Therefore the send (and receive) buffers for the unicast
 * socket have been increased (see ucast_send_buf_size and ucast_recv_buf_size below).<p>
 * If we didn't do this, we would have some retransmission, slowing the state transfer down.
 *
 * How to build?
 *   mvn clean install dependency:copy-dependencies
 *
 * How to run?
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar -Djava.net.preferIPv4Stack=true com.kylin.jgroups.performance.LargeState -name node1 -props config.xml -provider -size 10000000
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar -Djava.net.preferIPv4Stack=true com.kylin.jgroups.performance.LargeState -name node2 -props config.xml -size 10000000
 *   
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar -Djava.net.preferIPv4Stack=true com.kylin.jgroups.performance.LargeState -name node1 -props config.xml -provider -size 10000000 -delay 1000
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar -Djava.net.preferIPv4Stack=true com.kylin.jgroups.performance.LargeState -name node2 -props config.xml -size 10000000
 * 
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar -Djava.net.preferIPv4Stack=true com.kylin.jgroups.performance.LargeState -name node1 -props config.xml -provider -size 10000000 
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar -Djava.net.preferIPv4Stack=true com.kylin.jgroups.performance.LargeState -name node2 -props config.xml -size 10000000 -delay 1000
 *   
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar -Djava.net.preferIPv4Stack=true com.kylin.jgroups.performance.LargeState -name node1 -props config.xml -provider -size 10000000 -provider_fails
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar -Djava.net.preferIPv4Stack=true com.kylin.jgroups.performance.LargeState -name node2 -props config.xml -size 10000000
 *  
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar -Djava.net.preferIPv4Stack=true com.kylin.jgroups.performance.LargeState -name node1 -props config.xml -provider -size 10000000
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar -Djava.net.preferIPv4Stack=true com.kylin.jgroups.performance.LargeState -name node2 -props config.xml -size 10000000 -requester_fails
 *   
 * Run results:
 *   			node2 send state transfer request to coordinator(node1)
 *              			          \|/
 *   			node1's getState() method invoked, write via OutputStream
 *              			          \|/
 *   			node2's setState() method invoked, read from InputStream                        
 */
public class LargeState extends ReceiverAdapter{
	
	private static final String PROVIDER = "State Provider";
	private static final String REQUESTER = "State Requester";
	
	boolean provider = true, provider_fails = false, requester_fails = false;
	long delay = 0;
	
	Channel channel;
	
	int size = 100000;
	
	long start, stop;
	
	int total_received = 0;
	
	public void start(boolean provider, int size, String props, boolean provider_fails, boolean requester_fails, long delay, String name) throws Exception{
		
		this.provider = provider;
		this.provider_fails = provider_fails;
		this.requester_fails = requester_fails;
		this.delay = delay;
		this.size = size;
		
		channel = new JChannel(props);
		channel.setReceiver(this);
		
		if(name != null) {
			channel.setName(name);
		}
		
		channel.connect("LargeStateChannel");
		
		MBeanServer server = Util.getMBeanServer();
        if(server == null){
        	throw new Exception("No MBeanServers found;" + "\nLargeState needs to be run with an MBeanServer present, or inside JDK 5");
        }
        JmxConfigurator.registerChannel((JChannel)channel, server, "jgroups", channel.getClusterName(), true);
        println("-- connected to channel");
        
		if (provider) {
			println("-- Waiting for other members to join and fetch large state");
			for (;;) {
				Util.sleep(10000);
			}
		}
		
		start = System.currentTimeMillis();
		
		try {
			println(REQUESTER + " [" + channel.getName() + "] send get State request");
			channel.getState(null, 0);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Util.close(channel);
		}
	}
	
	private void println(Object obj) {
		System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss,S]").format(new Date()) + " " + obj);
	}
	
	public void receive(Message msg) {
        println("-- received msg " + msg.getObject() + " from " + msg.getSrc());
    }

    public void viewAccepted(View new_view) {
        println("-- view: " + new_view);
    }
    
    public void setState(InputStream istream) throws Exception {
    	
    	println(REQUESTER + " " + channel.getName() + ": setState() invoked, Read State via InputStream");
    	
		total_received = 0;
		int received = 0;
		int index = 1;
		int frag_size = size / 10;
        while(true) {
			byte[] buf = new byte[frag_size];
			received = istream.read(buf);
            if(received < 0){
            	break;
            }
            if(delay > 0){
            	Util.sleep(delay);
            }
            println("Read from InputStream " + index++);
			total_received += received;
            if(requester_fails){
            	throw new Exception("booom - requester failed");
            }
        }

		stop = System.currentTimeMillis();
        println("<-- received " + Util.printBytes(total_received) + " in " + (stop-start) + "ms");
    }

    public void getState(OutputStream ostream) throws Exception {
    	
    	println(PROVIDER + " " + channel.getName() + ": getState() invoked, Write State via OutputStream");
    	
		int frag_size = size / 10;
		long bytes = 0;
		for (int i = 0; i < 10; i++) {
			byte[] buf = new byte[frag_size];
            ostream.write(buf);
			bytes += buf.length;
			println("Write via OutputStream " + (i + 1));
            if(provider_fails){
            	throw new Exception("booom - provider failed");
            }
            if(delay > 0){
            	Util.sleep(delay);
            }
        }
		int remaining = size - (10 * frag_size);
        if(remaining > 0) {
			byte[] buf = new byte[remaining];
			ostream.write(buf);
			bytes += buf.length;
        }
		println("--> wrote " + Util.printBytes(bytes));
	}

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
		
		try {
            new LargeState().start(provider, size, props, provider_fails, requester_fails, delay, name);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
	}

	public static void help() {
		System.out.println("LargeState [-help] [-size <size of state in bytes] [-provider] [-name name] " + "[-props <properties>] [-provider_fails] [-requester_fails] [-delay <ms>]");
	}

}
