package org.jboss.demo.cluster.jpa.model;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;


@Stateful
public class UserDaoImpl implements UserDao {
	
	@Inject
    private EntityManager em;

	public String addUsers(List<User> users) {
		for(User user : users) {
			em.persist(user);
		}
		String content = "Add " + users.size() + " users to database";
		
		System.out.println(content);
		
		return content ;
	}

	public List<User> getUsers(String name) {
		
		return em.createQuery("select u from User u where u.name = ?", User.class).setParameter(1, name).getResultList();
	}


}
