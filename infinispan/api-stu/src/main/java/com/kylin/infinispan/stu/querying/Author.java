package com.kylin.infinispan.stu.querying;

import org.hibernate.search.annotations.Field;

public class Author {

	@Field
	String name;

	@Field
	String surname;

	public int hashCode() {
		return name.hashCode() + surname.hashCode();
	}

	public boolean equals(Object obj) {
		
		boolean result = false ;
		
		if(obj instanceof Author) {
			Author a = (Author) obj;
			return a.name == name && a.surname ==  surname ;
		}
		
		return result;
	}
}
