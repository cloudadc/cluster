package org.jboss.demo.cluster.jpa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.jboss.demo.cluster.jpa.model.User;
import org.jboss.demo.cluster.jpa.model.UserDao;


@Path("/")
public class JPAClusterDemoResource {
	
	@Inject
    private UserDao userDao;
	
	Random r = new Random();
	
	@GET
	@Path("/test/addUsers/{number}")
	public String addUser( @PathParam("number") int number) {
		
		List<User> users = new ArrayList<User>();
		for(int i = 1 ; i <= number ; i ++){
			users.add(new User("name-" + r.nextInt(5), new byte[100]));
		}
			
		return userDao.addUsers(users);
	}
	
	@GET
	@Path("/test/getUserByName/{name}")
	public String getUser(@PathParam("name") String name) {
		
		Long start = System.currentTimeMillis();
		
		List<User> users = userDao.getUsers(name);
		
		Long end = System.currentTimeMillis();
		
		String content = "Get " + users.size() + " users spend " + (end -start) + " milliseconds";
		
		System.out.println(content);
		
		return content;
	}

}
