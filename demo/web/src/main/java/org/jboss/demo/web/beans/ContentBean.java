package org.jboss.demo.web.beans;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.jboss.demo.web.events.Content;


@Named
@SessionScoped
public class ContentBean implements Serializable {

	private static final long serialVersionUID = -2129634854762195272L;
	
	@Inject
	Event<Content> contentProducer;
	
	private String content = "key";
	
	private String sessionIDPrefix = "Current Session ID: ";
	
	private String sessionID = "Current Session ID: ";
	
	public Event<Content> getContentProducer() {
		return contentProducer;
	}

	public void setContentProducer(Event<Content> contentProducer) {
		this.contentProducer = contentProducer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSessionID() {
	
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String add() {
		
		Content currentEvtPayload = new Content();
		currentEvtPayload.setDatetime(new Date());
		currentEvtPayload.setContent(content);
		currentEvtPayload.setServer(initServerStr());
		
		contentProducer.fire(currentEvtPayload);
				
		return "index";
	}
	
	private String initServerStr() {
		
		return System.getProperty("jboss.bind.address") + " - " + System.getProperty("jboss.node.name");
	}

	public void reset() {
				
		content = null;
	}
}
