drop table POTENTIAL_PARTICIPANTS;
create table POTENTIAL_PARTICIPANTS (
        workshop_id bigint auto_increment,
        user_id bigint not null,
        primary key (workshop_id, user_id)
    );


drop table user;
create table USER (
        id bigint auto_increment,
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        picture varchar(255),
        role varchar(255),
        charteragree tinyint(1),
        primary key (id)
    );


drop table WORKSHOP;
create table WORKSHOP (
        id bigint auto_increment,
        description varchar(1000),
        image varchar(255),
        subject varchar(255),
        author_id bigint,
        workshopSession_id bigint,
        primary key (id)
    );


drop table WORKSHOP_SESSION;
create table WORKSHOP_SESSION (
        id bigint auto_increment,
        doodle_url varchar(255),
        location varchar(255),
        nextPlay timestamp,
        speaker_id bigint,
        primary key (id)
    );


drop table WORKSHOP_SPEAKERS;
create table WORKSHOP_SPEAKERS (
        workshop_id bigint auto_increment,
        user_id bigint not null,
        primary key (workshop_id, user_id)
    );

    CREATE TABLE COMMENT (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  creation_date date NOT NULL,
  comment varchar(500) NOT NULL,
  author_id bigint(20) NOT NULL,
  workshop_id bigint(20) NOT NULL,
  PRIMARY KEY (id)
)

    alter table POTENTIAL_PARTICIPANTS 
        add constraint FK8118E64147140EFE 
        foreign key (user_id) 
        references USER;


    alter table POTENTIAL_PARTICIPANTS 
        add constraint FK8118E641AC90337E 
        foreign key (workshop_id) 
        references WORKSHOP;


    alter table WORKSHOP 
        add constraint FK30C7A187A7CD013E 
        foreign key (author_id) 
        references USER;

    alter table WORKSHOP 
        add constraint FK30C7A1873F837396 
        foreign key (workshopSession_id) 
        references WORKSHOP_SESSION;

    alter table WORKSHOP_SESSION 
        add constraint FKE158DFBEAE35BE4A 
        foreign key (speaker_id) 
        references USER;

    alter table WORKSHOP_SPEAKERS 
        add constraint FK76C6E80C47140EFE 
        foreign key (user_id) 
        references USER;

    alter table WORKSHOP_SPEAKERS 
        add constraint FK76C6E80CAC90337E 
        foreign key (workshop_id) 
        references WORKSHOP;
        