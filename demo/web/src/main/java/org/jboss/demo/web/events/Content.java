package org.jboss.demo.web.events;

import java.io.Serializable;
import java.util.Date;

public class Content implements  Serializable{

	private static final long serialVersionUID = 1791784829142993458L;

	private int id;
	
	private Date datetime;
	
	private String content;
	
	private String server;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
	
	public String toString(){
		return "EVT:"+getDatetime()+" ("+ getContent() + ") <<" + getServer() + ">>";
	}
}
