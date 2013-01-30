package com.kylin.tankwar.jgroups.deprecated;

import org.jgroups.conf.ProtocolStackConfigurator;

@Deprecated
public class ProtocolStackConfigInfo {

	private final String name;
	private final String description;
	private final ProtocolStackConfigurator configurator;
	   
	public ProtocolStackConfigInfo(String name, String description, ProtocolStackConfigurator configurator) {
		
		if (name == null) {
			throw new IllegalArgumentException("null name");
		}
	      
		if (configurator == null) {
			throw new IllegalArgumentException("null configurator");
		}
		
		this.name = name;
		this.description = description;
		this.configurator = configurator;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	ProtocolStackConfigurator getConfigurator() {
		return configurator;
	}

}
