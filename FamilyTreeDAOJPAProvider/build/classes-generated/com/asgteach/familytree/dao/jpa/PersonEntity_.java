package com.asgteach.familytree.dao.jpa;

import com.asgteach.familytree.dao.jpa.ChildParentEventEntity;
import com.asgteach.familytree.dao.jpa.DivorceEntity;
import com.asgteach.familytree.dao.jpa.EventEntity;
import com.asgteach.familytree.dao.jpa.MarriageEntity;
import com.asgteach.familytree.dao.jpa.ParentChildEventEntity;
import com.asgteach.familytree.dao.jpa.PictureEntity;
import com.asgteach.familytree.model.Person.Gender;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2017-03-17T19:11:21")
@StaticMetamodel(PersonEntity.class)
public class PersonEntity_ { 

    public static volatile SingularAttribute<PersonEntity, String> firstname;
    public static volatile SingularAttribute<PersonEntity, String> notes;
    public static volatile SingularAttribute<PersonEntity, Gender> gender;
    public static volatile SetAttribute<PersonEntity, ChildParentEventEntity> childEvents;
    public static volatile SingularAttribute<PersonEntity, String> middlename;
    public static volatile SingularAttribute<PersonEntity, String> suffix;
    public static volatile SingularAttribute<PersonEntity, Integer> version;
    public static volatile SingularAttribute<PersonEntity, PictureEntity> picture;
    public static volatile SingularAttribute<PersonEntity, String> lastname;
    public static volatile SetAttribute<PersonEntity, DivorceEntity> divorceEvents;
    public static volatile SetAttribute<PersonEntity, MarriageEntity> marriageEvents;
    public static volatile SetAttribute<PersonEntity, ParentChildEventEntity> parentEvents;
    public static volatile SingularAttribute<PersonEntity, Long> id;
    public static volatile ListAttribute<PersonEntity, EventEntity> events;

}