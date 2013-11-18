package org.jboss.demo.cluster.slsb;

import java.util.List;

import org.jboss.demo.cluster.entity.Person;

public interface StatelessSession {
	
	public String getServer();

	public void createPerson(Person person);

	public List<Person> findPersons();
	
	public String getName(Long pk);
	
	public void replacePerson(Long pk, String name);
	
	public void sendMessage(String message, Integer messageCount, Long processingDelay) throws Exception;
}
