package com.jdev.domain.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Title.class)
public abstract class Title_ extends com.jdev.domain.domain.AbstractIdentifiable_ {

	public static volatile SingularAttribute<Title, String> title;
	public static volatile ListAttribute<Title, Preview> preview;
	public static volatile SingularAttribute<Title, Date> dateTime;

}

