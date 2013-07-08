package com.kylin.jbosscache.ejb;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;

import org.jboss.annotation.ejb.Clustered;

@Stateful
@Remote(CounterService.class)
@Local(CounterServiceLocal.class)
@Clustered
public class CounterSession implements CounterServiceLocal {

	int counter = 0;

	public int count() {
		counter++;
		System.out.println("Value of counter is " + counter);
		return counter;
	}

}
