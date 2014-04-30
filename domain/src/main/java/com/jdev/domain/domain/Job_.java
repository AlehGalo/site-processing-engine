package com.jdev.domain.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Job.class)
public abstract class Job_ extends com.jdev.domain.domain.AbstractIdentifiable_ {

	public static volatile SingularAttribute<Job, Date> startTime;
	public static volatile SingularAttribute<Job, String> status;
	public static volatile SingularAttribute<Job, String> reasonOfStopping;
	public static volatile SingularAttribute<Job, Integer> errorsCount;
	public static volatile SingularAttribute<Job, Date> endTime;

}

