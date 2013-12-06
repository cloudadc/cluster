package org.jboss.demo.cluster.slsb;

import javax.ejb.Remote;
import javax.ejb.Stateful;

import org.jboss.ejb3.annotation.Clustered;

@Stateful
@Remote(StatefulSession.class)
@Clustered
public class StatefulSessionBean implements StatefulSession {

	public String getServer() {
		StringBuffer sb = new StringBuffer();
        
        String ip = System.getProperty("jboss.bind.address");
        if(null != ip) {
                sb.append("jboss.bind.address: " + ip);
                sb.append(", jboss.node.name: ");
        }
        
        String jbossNodeName = System.getProperty("jboss.node.name");
        if(null != jbossNodeName) {
                sb.append(jbossNodeName);
        }
        
        String result = sb.toString();
        
        System.out.println(result);
        
        return result;
	}
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
