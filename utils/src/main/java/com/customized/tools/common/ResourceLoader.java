package com.customized.tools.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class ResourceLoader {
	
	static Set<File> baseDir = new HashSet<File>();

	private static ResourceLoader instance;

	private ResourceLoader() {
		registerBaseDir(new File("").getAbsolutePath());
	}

	public static ResourceLoader getInstance() {

		if (null == instance)
			instance = new ResourceLoader();
		
		return instance;
	}
	
	public static ResourceLoader newInstance() {

		if (null == instance)
			instance = new ResourceLoader();
		
		return instance;
	}
	
	public static void unRegisterDir(String dir) {
		baseDir.remove(dir);
	}
	
	public static void registerBaseDir(String dir) {
		
		if(!new File(dir).exists() || !new File(dir).isDirectory()) {
			throw new IllegalArgumentException(dir + " should be a directory");
		}
		
		baseDir.add(new File(dir));
	}
	
	public InputStream getResourceAsStream(String name) {
		
		InputStream in = null ;
		
		//if relative configuration file path passed
		for(File file : baseDir) {
			try {
				in = new FileInputStream(new File(file, name));
				return in ;
			} catch (FileNotFoundException e) {
			}
		}
		
		// if absolute configuration file path passed
		if(null == in && new File(name).exists()){
			try {
				in = new FileInputStream(new File(name));
				return in ;
			} catch (FileNotFoundException e) {
			}
		}
		
		if(null == in)
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
		
		if(null == in)
			throw new RuntimeException("can not load " + name + " as stream");
		
		return in ;
	}
	
	public File getResourceAsFile (String name) {
	
		for(File file : baseDir) {
			if(new File(file, name).exists()) {
				return new File(file, name) ;
			}
		}
		
		throw new RuntimeException("Can not find " + name);
	}
	
	public boolean isExist(String conf) {
		
		boolean isExist = false;
		
		for(File file : baseDir){
			if(new File(file, conf).exists()) {
				isExist = true ;
				break;
			}
		} 
		
		return isExist;
	}
	
	public void printAllFiles(String ...filters) {
		
		Set<String> set = new HashSet<String>();
		for(String str : filters) {
			set.add(str);
		}
		
		StringBuffer sb = new StringBuffer();
		for(File file : baseDir) {
			for(File f : file.listFiles()) {
				String str = f.getName();
				if(str.endsWith(".xml") && !set.contains(str)){
					sb.append("   " + str + "\n");
				}
			}
		}
		System.out.println(sb.toString());
	}
	
	public String getAllConfFiles() {
		StringBuffer sb = new StringBuffer();
		for(File file : baseDir) {
			for(File f : file.listFiles()) {
				String str = f.getName();
				if(str.endsWith(".xml") ){
					sb.append("   " + str);
				}
			}
		}
		return sb.toString();
	}
	
	public String getAllConfFiles(String ...filters) {
		
		Set<String> set = new HashSet<String>();
		for(String str : filters) {
			set.add(str);
		}
		
		StringBuffer sb = new StringBuffer();
		for(File file : baseDir) {
			for(File f : file.listFiles()) {
				String str = f.getName();
				if(str.endsWith(".xml") && !set.contains(str)){
					sb.append("   " + str);
				}
			}
		}
		return sb.toString();
	}
}
