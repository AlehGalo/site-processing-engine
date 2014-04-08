package com.jdev.domain.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Preview.class)
public abstract class Preview_ extends com.jdev.domain.domain.AbstractIdentifiable_ {

	public static volatile SingularAttribute<Preview, Title> title;
	public static volatile SingularAttribute<Preview, Article> article;
	public static volatile SingularAttribute<Preview, String> preview;
	public static volatile SingularAttribute<Preview, ScaledImage> scaledImage;

}

