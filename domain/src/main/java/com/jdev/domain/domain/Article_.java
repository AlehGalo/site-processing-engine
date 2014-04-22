package com.jdev.domain.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Article.class)
public abstract class Article_ extends com.jdev.domain.domain.AbstractIdentifiable_ {

	public static volatile SingularAttribute<Article, String> content;
	public static volatile SingularAttribute<Article, String> title;
	public static volatile SingularAttribute<Article, Job> job;

}

