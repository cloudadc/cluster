package com.kylin.tankwar.jgroups.factory;

import java.util.Map;

public interface ProtocolDefaults {

	Map<String, String> getProperties(String protocol);
}
