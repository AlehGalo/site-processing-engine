package com.jdev.domain.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Job.class)
public abstract class Job_ extends com.jdev.domain.entity.AbstractIdentifiable_ {

	public static volatile SingularAttribute<Job, Date> startTime;
	public static volatile SingularAttribute<Job, Integer> crawlerErrorsCount;
	public static volatile SingularAttribute<Job, JobStatusEnum> status;
	public static volatile SingularAttribute<Job, Integer> databaseErrorsCount;
	public static volatile SingularAttribute<Job, Date> endTime;
	public static volatile SingularAttribute<Job, Credential> credential;
	public static volatile SingularAttribute<Job, Integer> recordsCount;

}

