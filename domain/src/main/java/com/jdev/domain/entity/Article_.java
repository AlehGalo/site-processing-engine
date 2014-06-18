package com.jdev.domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Article.class)
public abstract class Article_ extends com.jdev.domain.entity.AbstractIdentifiable_ {

	public static volatile SingularAttribute<Article, String> content;
	public static volatile SingularAttribute<Article, String> title;
	public static volatile SingularAttribute<Article, String> originalArticleUrl;
	public static volatile SingularAttribute<Article, Job> job;

}

