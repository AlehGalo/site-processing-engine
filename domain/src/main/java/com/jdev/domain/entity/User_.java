package com.jdev.domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.jdev.domain.entity.AbstractAuditingEntity_ {

	public static volatile SetAttribute<User, PersistentToken> persistentTokens;
	public static volatile SingularAttribute<User, String> activationKey;
	public static volatile SingularAttribute<User, String> lastName;
	public static volatile SetAttribute<User, Authority> authorities;
	public static volatile SingularAttribute<User, String> langKey;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, Boolean> activated;
	public static volatile SingularAttribute<User, String> login;
	public static volatile SingularAttribute<User, String> firstName;
	public static volatile SingularAttribute<User, String> password;

}

