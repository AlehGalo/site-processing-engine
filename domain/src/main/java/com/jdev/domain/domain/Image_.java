package com.jdev.domain.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Image.class)
public abstract class Image_ extends com.jdev.domain.domain.AbstractIdentifiable_ {

	public static volatile SingularAttribute<Image, byte[]> source;
	public static volatile SingularAttribute<Image, Integer> length;
	public static volatile SingularAttribute<Image, String> contentType;
	public static volatile SingularAttribute<Image, String> url;

}

