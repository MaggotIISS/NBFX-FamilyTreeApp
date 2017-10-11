package com.asgteach.familytree.dao.jpa;

import com.asgteach.familytree.dao.jpa.PersonEntity;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2017-03-17T19:11:21")
@StaticMetamodel(EventEntity.class)
public abstract class EventEntity_ { 

    public static volatile SingularAttribute<EventEntity, String> country;
    public static volatile SingularAttribute<EventEntity, String> town;
    public static volatile SingularAttribute<EventEntity, PersonEntity> person;
    public static volatile SingularAttribute<EventEntity, String> eventName;
    public static volatile SingularAttribute<EventEntity, String> state_province;
    public static volatile SingularAttribute<EventEntity, Long> id;
    public static volatile SingularAttribute<EventEntity, Integer> version;
    public static volatile SingularAttribute<EventEntity, Date> eventDate;

}