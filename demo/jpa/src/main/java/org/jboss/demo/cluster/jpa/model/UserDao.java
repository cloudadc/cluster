package org.jboss.demo.cluster.jpa.model;

import java.util.List;

import javax.ejb.Local;

@Local
public interface UserDao {
	
	public String addUsers(List<User> users);
	
	public List<User> getUsers(String name) ;
}
