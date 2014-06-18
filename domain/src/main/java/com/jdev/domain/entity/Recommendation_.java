package com.jdev.domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Recommendation.class)
public abstract class Recommendation_ extends com.jdev.domain.entity.AbstractIdentifiable_ {

	public static volatile SingularAttribute<Recommendation, Article> article;
	public static volatile SingularAttribute<Recommendation, Boolean> vote;

}

