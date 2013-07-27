package org.infinispan.grid.demo.test.JCD001;

import java.util.ServiceLoader;


import org.infinispan.configuration.parsing.ConfigurationParser;

public class Diagnostic {

	public static void main(String[] args) {
		 ServiceLoader<ConfigurationParser> parsers = ServiceLoader.load(ConfigurationParser.class, Thread.currentThread().getContextClassLoader());
//		 for (ConfigurationParser<?> parser : parsers) {
//			 for (Namespace ns : parser.getSupportedNamespaces()) {
//				 System.out.println(new QName(ns.getUri(), ns.getRootElement()));
//			 }
//			 System.out.println();
//		 }
	}

}
