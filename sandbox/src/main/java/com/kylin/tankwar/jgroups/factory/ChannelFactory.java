package com.kylin.tankwar.jgroups.factory;

import java.io.File;
import java.net.URL;

import org.jgroups.JChannel;

public interface ChannelFactory {

	public void setMultiplexerConfig(File properties) throws Exception ;
	
	public void setMultiplexerConfig(String properties) throws Exception ;
	
	public void setMultiplexerConfig(URL properties) throws Exception ;
	
	public void setMultiplexerConfig(File properties, boolean replace) throws Exception ;
	
	public void setMultiplexerConfig(URL properties, boolean replace) throws Exception ;
	
	public void setMultiplexerConfig(String properties, boolean replace) throws Exception ;
	
	public JChannel createChannel(String stack_name) throws Exception ;
}
