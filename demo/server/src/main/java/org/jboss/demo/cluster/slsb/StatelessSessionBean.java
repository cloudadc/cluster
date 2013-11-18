package org.jboss.demo.cluster.slsb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.jboss.demo.cluster.entity.Person;

@Stateless
@Remote(StatelessSession.class)
//@Clustered
public class StatelessSessionBean implements StatelessSession {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(mappedName = "java:/queue/DistributedQueue")
	private Queue queue;

	public String getServer() {
		return System.getProperty( "jboss.server.name" );
	}

	public void createPerson(Person person) {
		entityManager.persist( person );
	}

	public List<Person> findPersons() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> criteriaQuery = builder.createQuery( Person.class );
		criteriaQuery.select( criteriaQuery.from( Person.class ) );
		List<Person> persons = entityManager.createQuery( criteriaQuery ).getResultList();
		return persons;
	}

	public String getName(Long pk) {
		Person entity = entityManager.find(Person.class, pk);
		if (entity == null) {
			return null;
		} else {
			return entity.getName();
		}
	}

	public void replacePerson(Long pk, String name) {
		Person entity = entityManager.find(Person.class, pk);
		if (entity != null) {
			entity.setName(name);
			entityManager.merge(entity);
		}
	}

	public void sendMessage(String message, Integer messageCount, Long processingDelay) throws JMSException {
		
		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		map.put( "delay", processingDelay );
		map.put( "message", message );
		Connection connection = connectionFactory.createConnection();
		try {
			Session session = connection.createSession( false, Session.AUTO_ACKNOWLEDGE );
			MessageProducer messageProducer = session.createProducer( queue );
			connection.start();
			for (int index = 1; index <= messageCount; index++) {
				map.put( "count", index );
				ObjectMessage objectMessage = session.createObjectMessage();
				objectMessage.setObject( map );
				messageProducer.send( objectMessage );
			}
		} finally {
			connection.close();
		}
	}

}
