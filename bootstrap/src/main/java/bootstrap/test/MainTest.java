package bootstrap.test;

import java.util.Properties;
import java.util.Set;

public class MainTest {

	public static void main(String[] args) {

		Properties properties = System.getProperties();  
        Set<Object> keys = properties.keySet();  
        for(Object key : keys){  
            System.out.println(key + ": " + properties.get(key));  
        } 
	}

}
