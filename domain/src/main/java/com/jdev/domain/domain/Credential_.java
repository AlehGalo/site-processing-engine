package com.jdev.domain.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Credential.class)
public abstract class Credential_ extends com.jdev.domain.domain.AbstractIdentifiable_ {

	public static volatile SingularAttribute<Credential, Site> site;
	public static volatile SingularAttribute<Credential, String> username;
	public static volatile SingularAttribute<Credential, String> password;

}

