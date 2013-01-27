package bootstrap.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Set;

public class MainTest {
	
	static final String DEMO_LOG_CONF = "log4j.xml";
	
	static void systemProperties(){
		Properties properties = System.getProperties();  
        Set<Object> keys = properties.keySet();  
        for(Object key : keys){  
            System.out.println(key + ": " + properties.get(key));  
        } 
	}

	public static void main(String[] args) throws FileNotFoundException {

		String configFile = "/home/kylin/work/project/TankWar/bootstrap/build/Demo/conf/fucker.xml";
		
		StringBuffer sb = new StringBuffer();
		sb.append(new File(configFile).getName() + " doesn't exist, available config files: ");
		for(File file : new File(configFile).getParentFile().listFiles()) {
			if(file.getName().compareTo(DEMO_LOG_CONF) != 0) {
				sb.append(file.getName() + ", ");
			}
		}
		
		throw new FileNotFoundException(sb.toString());
		
	}

}
