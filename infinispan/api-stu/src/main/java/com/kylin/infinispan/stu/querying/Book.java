package com.kylin.infinispan.stu.querying;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Resolution;

@Indexed
public class Book {

	@Field
	String title;
	
	@Field
	String description;
	
	@Field
	@DateBridge(resolution = Resolution.YEAR)
	Date publicationYear;
	
	@IndexedEmbedded
	Set<Author> authors = new HashSet<Author>();
}
