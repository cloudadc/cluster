package org.jgroups.demo.tankwar.jgroups.factory;

import java.util.Map;

public interface ProtocolConfiguration {

	String getName();

    boolean hasProperty(String property);

    Map<String, String> getProperties();
}
