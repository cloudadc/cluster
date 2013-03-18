package com.kylin.tankwar.jmx;

import java.util.ArrayList;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

import com.kylin.tankwar.jmx.annotations.MBean;
import com.kylin.tankwar.model.Tank;

public class JMXUtil {
	
	private static final Logger log = Logger.getLogger(JMXUtil.class);
	
	public static final String DOMAIN_NAME = "TankWar";
	
	public static MBeanServer getMBeanServer() {
		
		ArrayList<MBeanServer> servers = MBeanServerFactory.findMBeanServer(null);
		
		if (servers != null && !servers.isEmpty()) {
			
			for(int i =0 ; i < servers.size() ; i ++) {
				MBeanServer server = servers.get(i);
				if(DOMAIN_NAME.equalsIgnoreCase(server.getDefaultDomain())) {
					return server;
				}
			}
			
			return (MBeanServer) servers.get(0);
		} else {
			return MBeanServerFactory.createMBeanServer(DOMAIN_NAME);
		}
	}
	
	public static void registerTank(Tank tank, MBeanServer server, String name) throws MalformedObjectNameException, MBeanRegistrationException {
		internalRegister(tank, server, name);
	} 
	
	public static void registerObject(Object obj, MBeanServer server, String name) throws MalformedObjectNameException, MBeanRegistrationException {
		internalRegister(obj, server, name);
	} 
	
	private static void internalRegister(Object obj, MBeanServer server, String name) throws MalformedObjectNameException, MBeanRegistrationException {

		if (obj == null) {
			throw new IllegalArgumentException("Object being registered cannot be null");
		}
		
		if (server == null) {
			throw new IllegalArgumentException("MBean server used for registeration cannot be null");
		}

		try {
			ObjectName objName = getObjectName(obj, name);
			
			if (server.isRegistered(objName)) {
				log.warn("unregistering already registered MBean: " + objName);
					
				try {
					server.unregisterMBean(objName);
				} catch (InstanceNotFoundException e) {
					log.error("failed to unregister MBean " + e.getMessage());
				}
			}

			ResourceMBean res = new ResourceMBean(obj);
			server.registerMBean(res, objName);
		} catch (InstanceAlreadyExistsException e) {
			log.error("register MBean failed " + e.getMessage());
			throw new MBeanRegistrationException(e, "The @MBean objectName is not unique");
		} catch (NotCompliantMBeanException e) {
			log.error("register MBean failed " + e.getMessage());
			throw new MBeanRegistrationException(e);
		}

	}
	private static ObjectName getObjectName(Object obj, String name) throws MalformedObjectNameException {
		
		MBean resource = obj.getClass().getAnnotation(MBean.class);
        
		if (name != null && name.length() > 0) {
			return new ObjectName(name);
		} else if (resource.objectName() != null && resource.objectName().length() > 0) {
            return new ObjectName(resource.objectName());
        } else {
            throw new MalformedObjectNameException(obj + " of class " + obj.getClass() + " has an invalid object name");
        }
    }
	
	public static void main(String[] args) {
		MBeanServer server = JMXUtil.getMBeanServer();
		
		System.out.println(server);
	}

}
