# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table ACTION (
  id                        bigint auto_increment not null,
  author_id                 bigint,
  creation_date             datetime,
  title                     varchar(1000),
  description               varchar(1000),
  constraint pk_ACTION primary key (id))
;

create table COMMENT (
  id                        bigint auto_increment not null,
  author_id                 bigint,
  creation_date             datetime,
  comment                   varchar(1000),
  workshop_id               bigint,
  constraint pk_COMMENT primary key (id))
;

create table RESSOURCE (
  id                        bigint auto_increment not null,
  ws_support_file           varchar(255),
  ws_support_link           varchar(255),
  constraint pk_RESSOURCE primary key (id))
;

create table TYPE (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  color                     varchar(255),
  constraint pk_TYPE primary key (id))
;

create table USER (
  id                        bigint auto_increment not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  email                     varchar(255),
  picture                   varchar(255),
  role                      varchar(8) not null,
  charterAgree              tinyint(1) default 0,
  constraint ck_USER_role check (role in ('admin','standard')),
  constraint pk_USER primary key (id))
;

create table WORKSHOP (
  id                        bigint auto_increment not null,
  subject                   varchar(100),
  summary                   varchar(300),
  description               varchar(1000),
  image                     varchar(255),
  author_id                 bigint,
  event_type_id             bigint,
  creation_date             datetime,
  workshop_ressources_id    bigint,
  constraint pk_WORKSHOP primary key (id))
;

create table WORKSHOP_SESSION (
  id                        bigint auto_increment not null,
  location                  varchar(50),
  limite_place              integer(2) not null,
  next_play                 datetime,
  speaker_id                bigint,
  workshop_id               bigint,
  constraint pk_WORKSHOP_SESSION primary key (id))
;


create table WORKSHOP_SPEAKERS (
  workshop_id                    bigint not null,
  user_id                        bigint not null,
  constraint pk_WORKSHOP_SPEAKERS primary key (workshop_id, user_id))
;

create table POTENTIAL_PARTICIPANTS (
  workshop_id                    bigint not null,
  user_id                        bigint not null,
  constraint pk_POTENTIAL_PARTICIPANTS primary key (workshop_id, user_id))
;

create table PARTICIPANTS (
  workshop_id                    bigint not null,
  user_id                        bigint not null,
  constraint pk_PARTICIPANTS primary key (workshop_id, user_id))
;
alter table ACTION add constraint fk_ACTION_author_1 foreign key (author_id) references USER (id) on delete restrict on update restrict;
create index ix_ACTION_author_1 on ACTION (author_id);
alter table COMMENT add constraint fk_COMMENT_author_2 foreign key (author_id) references USER (id) on delete restrict on update restrict;
create index ix_COMMENT_author_2 on COMMENT (author_id);
alter table COMMENT add constraint fk_COMMENT_workshop_3 foreign key (workshop_id) references WORKSHOP (id) on delete restrict on update restrict;
create index ix_COMMENT_workshop_3 on COMMENT (workshop_id);
alter table WORKSHOP add constraint fk_WORKSHOP_author_4 foreign key (author_id) references USER (id) on delete restrict on update restrict;
create index ix_WORKSHOP_author_4 on WORKSHOP (author_id);
alter table WORKSHOP add constraint fk_WORKSHOP_eventType_5 foreign key (event_type_id) references TYPE (id) on delete restrict on update restrict;
create index ix_WORKSHOP_eventType_5 on WORKSHOP (event_type_id);
alter table WORKSHOP add constraint fk_WORKSHOP_workshopRessources_6 foreign key (workshop_ressources_id) references RESSOURCE (id) on delete restrict on update restrict;
create index ix_WORKSHOP_workshopRessources_6 on WORKSHOP (workshop_ressources_id);
alter table WORKSHOP_SESSION add constraint fk_WORKSHOP_SESSION_speaker_7 foreign key (speaker_id) references USER (id) on delete restrict on update restrict;
create index ix_WORKSHOP_SESSION_speaker_7 on WORKSHOP_SESSION (speaker_id);
alter table WORKSHOP_SESSION add constraint fk_WORKSHOP_SESSION_workshop_8 foreign key (workshop_id) references WORKSHOP (id) on delete restrict on update restrict;
create index ix_WORKSHOP_SESSION_workshop_8 on WORKSHOP_SESSION (workshop_id);



alter table WORKSHOP_SPEAKERS add constraint fk_WORKSHOP_SPEAKERS_WORKSHOP_01 foreign key (workshop_id) references WORKSHOP (id) on delete restrict on update restrict;

alter table WORKSHOP_SPEAKERS add constraint fk_WORKSHOP_SPEAKERS_USER_02 foreign key (user_id) references USER (id) on delete restrict on update restrict;

alter table POTENTIAL_PARTICIPANTS add constraint fk_POTENTIAL_PARTICIPANTS_WORKSHOP_01 foreign key (workshop_id) references WORKSHOP (id) on delete restrict on update restrict;

alter table POTENTIAL_PARTICIPANTS add constraint fk_POTENTIAL_PARTICIPANTS_USER_02 foreign key (user_id) references USER (id) on delete restrict on update restrict;

alter table PARTICIPANTS add constraint fk_PARTICIPANTS_WORKSHOP_SESSION_01 foreign key (workshop_id) references WORKSHOP_SESSION (id) on delete restrict on update restrict;

alter table PARTICIPANTS add constraint fk_PARTICIPANTS_USER_02 foreign key (user_id) references USER (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table ACTION;

drop table COMMENT;

drop table RESSOURCE;

drop table TYPE;

drop table USER;

drop table WORKSHOP;

drop table WORKSHOP_SPEAKERS;

drop table POTENTIAL_PARTICIPANTS;

drop table WORKSHOP_SESSION;

drop table PARTICIPANTS;

SET FOREIGN_KEY_CHECKS=1;

