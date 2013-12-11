package org.jboss.demo.web.handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.jboss.demo.web.events.Content;


@SessionScoped
public class ContentHandler implements Serializable {

	private static final long serialVersionUID = -3093559590034641607L;
	
	private static final Logger logger = Logger.getLogger(ContentHandler.class);
	
	
	List<Content> contents = new ArrayList<Content>();
	
	@Produces
	@Named
	public List<Content> getContents() {
		return contents;
	}


	public void onEvent(@Observes Content event) {
		
		logger.info("Processing event: " + event);
		
		contents.add(event);
		
		addToDB(event);
	}
	
	

	private void addToDB(Content event) {
		
		logger.debug("persit event to DB");
		
		
	}
}
