package com.kylin.jbosscache.demo;

import java.io.Serializable;

public class Content implements Serializable {

	private static final long serialVersionUID = 8638242060438288435L;
	
	private String content;

	public Content(String content) {
		super();
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String toString() {
		return "Content [content=" + content + "]";
	}

}
