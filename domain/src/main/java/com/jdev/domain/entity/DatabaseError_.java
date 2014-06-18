package com.jdev.domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DatabaseError.class)
public abstract class DatabaseError_ extends com.jdev.domain.entity.AbstractIdentifiable_ {

	public static volatile SingularAttribute<DatabaseError, String> error;
	public static volatile SingularAttribute<DatabaseError, Job> job;
	public static volatile SingularAttribute<DatabaseError, String> url;

}

