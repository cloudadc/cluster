package com.kylin.jbosscache.ejb;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.jboss.annotation.ejb.Clustered;

@Stateless
@Remote(HelloService.class)
@Local(HelloServiceLocal.class)
@Clustered
public class HelloSession implements HelloServiceLocal {

	public void sayHello() {
		System.out.println("Hello EJB3 cluster");
	}
}
