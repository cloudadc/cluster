package com.kylin.tankwar.jgroups.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.jgroups.conf.XmlConfigurator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ProtocolStackUtil {
	
	private static final Logger log = Logger.getLogger(ProtocolStackUtil.class);
	
	private static final String PROTOCOL_STACKS = "protocol_stacks";
	private static final String STACK="stack";
	private static final String NAME = "name";
	private static final String DESCR = "description";
	private static final String CONFIG="config";

	public static Map<String, ProtocolStackConfigInfo> parse(InputStream input) throws Exception {
		
		if (input == null) {
			throw new IllegalArgumentException("null input");
		}
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		factory.setValidating(false); // for now
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(input);
		
		Element configElement = document.getDocumentElement();
		return parse(configElement);
	}
	
	public static Map<String, ProtocolStackConfigInfo> parse(Element root) throws Exception  {
		
		if (root == null) {
			throw new IllegalArgumentException("null root");
		}
		
		String root_name = root.getNodeName();
		if (!PROTOCOL_STACKS.equals(root_name.trim().toLowerCase())) {
	         throw new IOException("Invalid XML configuration: configuration does not start with a '" +  PROTOCOL_STACKS + "' element");
		}
		
		Map<String, ProtocolStackConfigInfo> result = new HashMap<String, ProtocolStackConfigInfo>();
		
		NodeList tmp_stacks = root.getChildNodes();
		for (int i = 0; i < tmp_stacks.getLength(); i++) {
			Node node = tmp_stacks.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			Element stack = (Element) node;
			String tmp = stack.getNodeName();
			if (!STACK.equals(tmp.trim().toLowerCase())) {
				throw new IOException("Invalid configuration: didn't find a \"" + STACK + "\" element under \"" + PROTOCOL_STACKS + "\"");
			}

			NamedNodeMap attrs = stack.getAttributes();
			Node name = attrs.getNamedItem(NAME);
			String st_name = name.getNodeValue();
			Node descr = attrs.getNamedItem(DESCR);
			String stack_descr=descr.getNodeValue();
			
			if (log.isTraceEnabled()) {
				log.trace("Parsing \"" + st_name + "\" (" + stack_descr + ")");
			}
			
			NodeList configs = stack.getChildNodes();
			for (int j = 0; j < configs.getLength(); j++) {
				Node tmp_config = configs.item(j);
				if (tmp_config.getNodeType() != Node.ELEMENT_NODE){
					continue;
				}
				Element cfg = (Element) tmp_config;
				tmp = cfg.getNodeName();
				if (!CONFIG.equals(tmp)) {
	               throw new IOException("Invalid configuration: didn't find a \"" +  CONFIG + "\" element under \"" + STACK + "\"");
				}

	            XmlConfigurator conf = XmlConfigurator.getInstance(cfg);
	            // fixes http://jira.jboss.com/jira/browse/JGRP-290

	            result.put(st_name, new ProtocolStackConfigInfo(st_name, stack_descr, conf));
	         }
	      }

		
		return result ;
	}

}
