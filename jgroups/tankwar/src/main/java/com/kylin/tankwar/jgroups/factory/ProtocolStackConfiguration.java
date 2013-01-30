package com.kylin.tankwar.jgroups.factory;

import java.util.List;

import javax.management.MBeanServer;

/**
 * Defines the configuration of a JGroups protocol.
 * 
 * @author kylin
 *
 */
public interface ProtocolStackConfiguration {

	String getName();

    ProtocolDefaults getDefaults();
    
    MBeanServer getMBeanServer();
    
    TransportConfiguration getTransport();
    
    List<ProtocolConfiguration> getProtocols();
    
    String getNodeName();
}
