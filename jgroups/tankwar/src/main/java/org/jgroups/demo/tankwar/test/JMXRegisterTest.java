package org.jgroups.demo.tankwar.test;

import javax.management.MBeanServer;

import org.jgroups.demo.tankwar.jmx.JMXUtil;
import org.jgroups.demo.tankwar.model.Direction;
import org.jgroups.demo.tankwar.model.Tank;


/**
 *  How to run?
 *    1. build
 *        mvn clean dependency:copy-dependencies install
 *    2. run
 *        java -cp jgroups-3.1.0.Final.jar:log4j-1.2.16.jar:tankwar-1.0.jar com.kylin.tankwar.test.JMXRegisterTest    
 *    
 * 
 * @author kylin
 *
 */
public class JMXRegisterTest {

	public JMXRegisterTest() {
	}
	
	public static void main(String[] args) throws Exception {
		
		Tank tank = new Tank("myTank", true, true, 100, 100, 100, Direction.LU, Direction.LU, null);
		
		MBeanServer server = JMXUtil.getMBeanServer();

//		JMXUtil.registerTank(tank, server, "TankWar:type=View,name=tank");
		
		JMXTestEntity entity = new JMXTestEntity("jmx register test", 100);
		
		JMXUtil.registerObject(entity, server, "TankWar:type=Entity,name=test");
		
		Thread.sleep(Long.MAX_VALUE);
		
	}

}
