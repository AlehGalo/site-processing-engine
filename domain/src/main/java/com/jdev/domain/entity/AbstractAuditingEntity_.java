package com.jdev.domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.joda.time.DateTime;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AbstractAuditingEntity.class)
public abstract class AbstractAuditingEntity_ {

	public static volatile SingularAttribute<AbstractAuditingEntity, String> createdBy;
	public static volatile SingularAttribute<AbstractAuditingEntity, String> lastModifiedBy;
	public static volatile SingularAttribute<AbstractAuditingEntity, DateTime> lastModifiedDate;
	public static volatile SingularAttribute<AbstractAuditingEntity, DateTime> createdDate;

}

