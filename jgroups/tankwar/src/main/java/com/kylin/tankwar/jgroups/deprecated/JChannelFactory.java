package com.kylin.tankwar.jgroups.deprecated;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.conf.ConfiguratorFactory;
import org.jgroups.conf.ProtocolStackConfigurator;
import org.jgroups.util.Util;

@Deprecated
public class JChannelFactory implements ChannelFactory{
	
	private static final Logger log = Logger.getLogger(JChannelFactory.class);
	
	private final Map<String,ProtocolStackConfigInfo> stacks = new ConcurrentHashMap<String, ProtocolStackConfigInfo>(16, 0.75f, 2);
	
	public void setMultiplexerConfig(File properties) throws Exception {
		setMultiplexerConfig(properties, true);
	}
	
	public void setMultiplexerConfig(String properties) throws Exception {
		setMultiplexerConfig(properties, true);
	}
	
	public void setMultiplexerConfig(URL properties) throws Exception {
		setMultiplexerConfig(properties, true);
	}
	
	public void setMultiplexerConfig(File properties, boolean replace) throws Exception {
		InputStream input = ConfiguratorFactory.getConfigStream(properties);
		addConfigs(input, properties.toString(), replace);
	}
	
	public void setMultiplexerConfig(URL properties, boolean replace) throws Exception {
		InputStream input = ConfiguratorFactory.getConfigStream(properties);
		addConfigs(input, properties.toString(), replace);
	}
	
	public void setMultiplexerConfig(String properties, boolean replace) throws Exception {
		InputStream input = ConfiguratorFactory.getConfigStream(properties);
		addConfigs(input, properties, replace);
	}
	
	private void addConfigs(InputStream input, String source, boolean replace) throws Exception {
		
		if (input == null) {
			throw new FileNotFoundException(source);
		}
		
		Map<String, ProtocolStackConfigInfo> map = null;
		try {
			map = ProtocolStackUtil.parse(input);
		} catch (Exception ex) {
			throw new Exception("failed parsing " + source, ex);
		} finally {
			Util.close(input);
		}
		
		for (Map.Entry<String, ProtocolStackConfigInfo> entry : map.entrySet()) {
			addConfig(entry.getKey(), entry.getValue(), replace);
		}
	}
	
	private boolean addConfig(String st_name, ProtocolStackConfigInfo val, boolean replace) {
		
		boolean added = replace;
		
		if (replace) {
			stacks.put(st_name, val);
			if (log.isTraceEnabled())
				log.trace("added config '" + st_name + "'");
		} else {
			if (!stacks.containsKey(st_name)) {
				stacks.put(st_name, val);
				if (log.isTraceEnabled())
					log.trace("added config '" + st_name + "'");
				added = true;
			} else {
				if (log.isTraceEnabled())
	               log.trace("didn't add config '" + st_name + " because one of the same name already existed");
			}
		}
		return added;
	}
	
	public void clearConfigurations() {
		this.stacks.clear();
	}

	public String dumpChannels() {
		return "";
	}

	public String dumpConfiguration() {
		return stacks.keySet().toString();
	}

	public String getConfig(String stack_name) throws Exception {
		ProtocolStackConfigInfo cfg = stacks.get(stack_name);
		if (cfg == null)
			throw new Exception("stack \"" + stack_name + "\" not found in " + stacks.keySet());
		return cfg.getConfigurator().getProtocolStackString();
	}

	public String getMultiplexerConfig() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, ProtocolStackConfigInfo> entry : stacks.entrySet()) {
			sb.append(entry.getKey()).append(": ").append(entry.getValue().getConfigurator().getProtocolStackString()).append("\n");
		}
		return sb.toString();
	}

	public boolean removeConfig(String stack_name) {
		return stack_name != null && this.stacks.remove(stack_name) != null;
	}
	
	public JChannel createChannel(String stack_name) throws Exception {
		return createChannelFromRegisteredStack(stack_name, null, false);
	}
	
	private JChannel createChannelFromRegisteredStack(String stack_name, String id, boolean forceSingletonStack) throws Exception {
	      
		ProtocolStackConfigInfo config = stacks.get(stack_name);
	      
		if (config == null)
			throw new IllegalArgumentException("Unknown stack_name " + stack_name);
	      
		JChannel channel = initializeChannel(config.getConfigurator(), stack_name);
	      
		return channel;
	}
	   
	private JChannel initializeChannel(ProtocolStackConfigurator config, String stack_name ) throws Exception  {  
	
	      JChannel channel = new JChannel(config);
	       
	      log.info("Create Channel via " + stack_name);
	      
	      return channel;
	   }

}
