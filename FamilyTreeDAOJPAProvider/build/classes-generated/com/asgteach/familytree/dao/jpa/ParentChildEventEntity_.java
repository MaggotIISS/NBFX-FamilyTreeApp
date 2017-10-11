package com.asgteach.familytree.dao.jpa;

import com.asgteach.familytree.dao.jpa.PersonEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2017-03-17T19:11:21")
@StaticMetamodel(ParentChildEventEntity.class)
public class ParentChildEventEntity_ extends EventEntity_ {

    public static volatile SingularAttribute<ParentChildEventEntity, Boolean> bioBirth;
    public static volatile SetAttribute<ParentChildEventEntity, PersonEntity> parents;

}