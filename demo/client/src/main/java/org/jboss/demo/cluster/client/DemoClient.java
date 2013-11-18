package org.jboss.demo.cluster.client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.demo.cluster.entity.Person;
import org.jboss.demo.cluster.sfsb.StatefulSession;
import org.jboss.demo.cluster.slsb.StatelessSession;

public class DemoClient {
	
	private String applicationContext  ;
	private String SFSB_JNDI = "ejb:/" + applicationContext + "/StatefulSessionBean!" + StatefulSession.class.getName() + "?stateful";
	private String SLSB_JNDI = "ejb:/" + applicationContext + "/StatelessSessionBean!" + StatelessSession.class.getName() ;
	
	private StatefulSession sfsb;
	private Context context;
	
	public DemoClient(String applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	private void execute() throws Exception {
		Hashtable<String, String> jndiProps = new Hashtable<String, String>();
		jndiProps.put( Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming" );
		context = new InitialContext( jndiProps );
		sfsb = (StatefulSession)context.lookup(SFSB_JNDI);
		String name = registerName();
		System.out.println();
		System.out.println( "Welcome " + name + ", your name has been registered on the following server: " + getStatefulSessionBean().getServer() );
		interactive();
	}
	
	private void interactive() throws Exception {
		System.out.println();
		System.out.println( "Enter:" );
		System.out.println( "1- Stateful: view the name currently registered on the running server" );
		System.out.println( "2- Stateful: set a new name on the running server" );
		System.out.println( "3- SLSB/JPA: create a person object" );
		System.out.println( "4- SLSB/JPA: get a person's name" );
		System.out.println( "5- SLSB/JPA: change a person's name" );
		System.out.println( "6- SLSB/JPA: list all person objects" );
		System.out.println( "7- SLSB/JMS: Send messages to the queue" );
		System.out.println( "8- To exit" );
		System.out.println();
		int choice = 0;
		while (choice < 1 || choice > 8) {
			String line = System.console().readLine();
			if (line.length() == 1) {
				choice = Character.digit( line.charAt( 0 ), 10 );
			}
		}
		switch (choice) {
			case 1:
				System.out.println( "\nYou are registered as " + getStatefulSessionBean().getName() + " on the following server: " + getStatefulSessionBean().getServer() );
				break;

			case 2:
				registerName();
				break;

			case 3:
				createPerson();
				break;

			case 4:
				getName();
				break;

			case 5:
				changePersonName();
				break;

			case 6:
				listPersons();
				break;

			case 7:
				sendMessage();
				break;

			default:
				return;

		}
		interactive();
	}
	
	private void createPerson() throws NamingException {
		System.out.println();
		System.out.print( "Enter your name: " );
		String name = System.console().readLine();
		StatelessSession slsb = getStatelessSessionBean();
		String server = slsb.getServer();
		slsb.createPerson( new Person( name ) );
		System.out.println();
		System.out.println( "Created person on server " + server );
	}
	
	private void getName() throws NamingException {
		System.out.println();
		System.out.print( "Enter person Id: " );
		String id = System.console().readLine();
		StatelessSession slsb = getStatelessSessionBean();
		String name = slsb.getName( Long.valueOf( id ) );
		System.out.println( "Person name is registered as " + name + " on " + slsb.getServer() );
	}
	
	private void changePersonName() throws NamingException {
		System.out.println();
		System.out.print( "Enter person Id: " );
		String id = System.console().readLine();
		System.out.print( "Enter new name: " );
		String name = System.console().readLine();
		StatelessSession slsb = getStatelessSessionBean();
		String server = slsb.getServer();
		slsb.replacePerson( Long.valueOf( id ), name );
		System.out.println( "Changed person name on server " + server );
	}

	private void listPersons() throws NamingException {
		StatelessSession slsb = getStatelessSessionBean();
		System.out.println( "Person objects stored in the database as received from " + slsb.getServer() + " are as follows:" );
		System.out.println(slsb.findPersons());
	}
	
	private String registerName() throws NamingException {
		System.out.println();
		System.out.println("Enter your name: ");
		String name = System.console().readLine();
		getStatefulSessionBean().setName(name);
		return name;
	}
	
	private void sendMessage() throws NamingException, Exception {
		System.out.println();
		System.out.print( "Content of message: " );
		String message = System.console().readLine();
		System.out.print( "Number of messages: " );
		Integer messageCount = Integer.valueOf( System.console().readLine() );
		System.out.print( "Delay in Milliseconds: " );
		Long processingDelay = Long.valueOf( System.console().readLine() );
		StatelessSession slsb = getStatelessSessionBean();
		slsb.sendMessage( message, messageCount, processingDelay );
	}
	
	private StatelessSession getStatelessSessionBean() throws NamingException {
		return (StatelessSession)context.lookup(SLSB_JNDI);
	}
	
	private StatefulSession getStatefulSessionBean() throws NamingException {
		return sfsb;
	}

	public static void main(String[] args) throws Exception {
		String applicationContext;
		if (args.length == 0) {
			applicationContext = "cluster-demo-server";
		} else {
			applicationContext = args[0];
		}
		new DemoClient(applicationContext).execute();
	}

}
