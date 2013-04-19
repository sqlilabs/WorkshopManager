	alter table COMMENT 
        drop 
        foreign key FK63717A3FA7CD013E;

    alter table COMMENT 
        drop 
        foreign key FK63717A3FAC90337E;

    alter table POTENTIAL_PARTICIPANTS 
        drop 
        foreign key FK8118E64147140EFE;

    alter table POTENTIAL_PARTICIPANTS 
        drop 
        foreign key FK8118E641AC90337E;

    alter table WORKSHOP 
        drop 
        foreign key FK30C7A187B1F406B7;

    alter table WORKSHOP 
        drop 
        foreign key FK30C7A187A7CD013E;

    alter table WORKSHOP 
        drop 
        foreign key FK30C7A1873F837396;

    alter table WORKSHOP_SESSION 
        drop 
        foreign key FKE158DFBEAE35BE4A;

    alter table WORKSHOP_SPEAKERS 
        drop 
        foreign key FK76C6E80C47140EFE;

    alter table WORKSHOP_SPEAKERS 
        drop 
        foreign key FK76C6E80CAC90337E;

    drop table if exists COMMENT;

    drop table if exists POTENTIAL_PARTICIPANTS;

    drop table if exists RESSOURCE;

    drop table if exists USER;

    drop table if exists WORKSHOP;

    drop table if exists WORKSHOP_SESSION;

    drop table if exists WORKSHOP_SPEAKERS;

    create table COMMENT (
        id bigint not null auto_increment,
        comment varchar(255),
        creation_date datetime,
        author_id bigint,
        workshop_id bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table POTENTIAL_PARTICIPANTS (
        workshop_id bigint not null,
        user_id bigint not null,
        primary key (workshop_id, user_id)
    ) ENGINE=InnoDB;

    create table RESSOURCE (
        id bigint not null auto_increment,
        ws_support_file varchar(255),
        ws_support_link varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table USER (
        id bigint not null auto_increment,
        charterAgree boolean,
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        picture varchar(255),
        role varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table WORKSHOP (
        id bigint not null auto_increment,
        description varchar(1000),
        image varchar(255),
        subject varchar(255),
        summary varchar(300),
        author_id bigint,
        workshopRessources_id bigint,
        workshopSession_id bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table WORKSHOP_SESSION (
        id bigint not null auto_increment,
        doodle_url varchar(255),
        location varchar(255),
        nextPlay datetime,
        speaker_id bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table WORKSHOP_SPEAKERS (
        workshop_id bigint not null,
        user_id bigint not null,
        primary key (workshop_id, user_id)
    ) ENGINE=InnoDB;

    alter table COMMENT 
        add index FK63717A3FA7CD013E (author_id), 
        add constraint FK63717A3FA7CD013E 
        foreign key (author_id) 
        references USER (id);

    alter table COMMENT 
        add index FK63717A3FAC90337E (workshop_id), 
        add constraint FK63717A3FAC90337E 
        foreign key (workshop_id) 
        references WORKSHOP (id);

    alter table POTENTIAL_PARTICIPANTS 
        add index FK8118E64147140EFE (user_id), 
        add constraint FK8118E64147140EFE 
        foreign key (user_id) 
        references USER (id);

    alter table POTENTIAL_PARTICIPANTS 
        add index FK8118E641AC90337E (workshop_id), 
        add constraint FK8118E641AC90337E 
        foreign key (workshop_id) 
        references WORKSHOP (id);

    alter table WORKSHOP 
        add index FK30C7A187B1F406B7 (workshopRessources_id), 
        add constraint FK30C7A187B1F406B7 
        foreign key (workshopRessources_id) 
        references RESSOURCE (id);

    alter table WORKSHOP 
        add index FK30C7A187A7CD013E (author_id), 
        add constraint FK30C7A187A7CD013E 
        foreign key (author_id) 
        references USER (id);

    alter table WORKSHOP 
        add index FK30C7A1873F837396 (workshopSession_id), 
        add constraint FK30C7A1873F837396 
        foreign key (workshopSession_id) 
        references WORKSHOP_SESSION (id);

    alter table WORKSHOP_SESSION 
        add index FKE158DFBEAE35BE4A (speaker_id), 
        add constraint FKE158DFBEAE35BE4A 
        foreign key (speaker_id) 
        references USER (id);

    alter table WORKSHOP_SPEAKERS 
        add index FK76C6E80C47140EFE (user_id), 
        add constraint FK76C6E80C47140EFE 
        foreign key (user_id) 
        references USER (id);

    alter table WORKSHOP_SPEAKERS 
        add index FK76C6E80CAC90337E (workshop_id), 
        add constraint FK76C6E80CAC90337E 
        foreign key (workshop_id) 
        references WORKSHOP (id);
