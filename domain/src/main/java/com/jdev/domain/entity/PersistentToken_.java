package com.jdev.domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.joda.time.LocalDate;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PersistentToken.class)
public abstract class PersistentToken_ {

	public static volatile SingularAttribute<PersistentToken, String> series;
	public static volatile SingularAttribute<PersistentToken, LocalDate> tokenDate;
	public static volatile SingularAttribute<PersistentToken, String> userAgent;
	public static volatile SingularAttribute<PersistentToken, String> tokenValue;
	public static volatile SingularAttribute<PersistentToken, User> user;
	public static volatile SingularAttribute<PersistentToken, String> ipAddress;

}

