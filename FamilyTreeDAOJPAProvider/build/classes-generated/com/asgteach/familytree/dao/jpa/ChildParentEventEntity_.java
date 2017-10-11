package com.asgteach.familytree.dao.jpa;

import com.asgteach.familytree.dao.jpa.PersonEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2017-03-17T19:11:21")
@StaticMetamodel(ChildParentEventEntity.class)
public class ChildParentEventEntity_ extends EventEntity_ {

    public static volatile SingularAttribute<ChildParentEventEntity, Boolean> bioBirth;
    public static volatile SingularAttribute<ChildParentEventEntity, PersonEntity> child;

}