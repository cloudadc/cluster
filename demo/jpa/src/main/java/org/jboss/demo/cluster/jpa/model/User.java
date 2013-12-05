package org.jboss.demo.cluster.jpa.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity(name = "t_user")
@Cacheable(true)
public class User implements Serializable {

	private static final long serialVersionUID = -237266891633115639L;
	
	@Column
    @Id
    @GeneratedValue
    private Long id;
	
	private String name;
	
	@Lob
	@Column( name = "CONTENT" )
	private byte[] content;
	
	public User() {
		
	}
	
	public User(String name, byte[] content) {
		this.name = name ;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

}
