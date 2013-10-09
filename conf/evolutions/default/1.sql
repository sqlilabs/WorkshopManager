# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table ACTION (
  id                        bigint not null,
  author_id                 bigint,
  creation_date             timestamp,
  title                     varchar(1000),
  description               varchar(1000),
  constraint pk_ACTION primary key (id))
;

create table COMMENT (
  id                        bigint not null,
  author_id                 bigint,
  creation_date             timestamp,
  comment                   varchar(1000),
  workshop_id               bigint,
  constraint pk_COMMENT primary key (id))
;

create table RESSOURCE (
  id                        bigint not null,
  ws_support_file           varchar(255),
  ws_support_link           varchar(255),
  constraint pk_RESSOURCE primary key (id))
;

create table USER (
  id                        bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  email                     varchar(255),
  picture                   varchar(255),
  role                      varchar(8) not null,
  charterAgree              boolean,
  constraint ck_USER_role check (role in ('admin','standard')),
  constraint pk_USER primary key (id))
;

create table WORKSHOP (
  id                        bigint not null,
  subject                   varchar(100),
  summary                   varchar(300),
  description               varchar(1000),
  image                     varchar(255),
  author_id                 bigint,
  creation_date             timestamp,
  workshop_ressources_id    bigint,
  constraint pk_WORKSHOP primary key (id))
;

create table WORKSHOP_SESSION (
  id                        bigint not null,
  location                  varchar(50),
  limite_place              integer(2) not null,
  next_play                 timestamp,
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
create sequence ACTION_seq;

create sequence COMMENT_seq;

create sequence RESSOURCE_seq;

create sequence USER_seq;

create sequence WORKSHOP_seq;

create sequence WORKSHOP_SESSION_seq;

alter table ACTION add constraint fk_ACTION_author_1 foreign key (author_id) references USER (id) on delete restrict on update restrict;
create index ix_ACTION_author_1 on ACTION (author_id);
alter table COMMENT add constraint fk_COMMENT_author_2 foreign key (author_id) references USER (id) on delete restrict on update restrict;
create index ix_COMMENT_author_2 on COMMENT (author_id);
alter table COMMENT add constraint fk_COMMENT_workshop_3 foreign key (workshop_id) references WORKSHOP (id) on delete restrict on update restrict;
create index ix_COMMENT_workshop_3 on COMMENT (workshop_id);
alter table WORKSHOP add constraint fk_WORKSHOP_author_4 foreign key (author_id) references USER (id) on delete restrict on update restrict;
create index ix_WORKSHOP_author_4 on WORKSHOP (author_id);
alter table WORKSHOP add constraint fk_WORKSHOP_workshopRessources_5 foreign key (workshop_ressources_id) references RESSOURCE (id) on delete restrict on update restrict;
create index ix_WORKSHOP_workshopRessources_5 on WORKSHOP (workshop_ressources_id);
alter table WORKSHOP_SESSION add constraint fk_WORKSHOP_SESSION_speaker_6 foreign key (speaker_id) references USER (id) on delete restrict on update restrict;
create index ix_WORKSHOP_SESSION_speaker_6 on WORKSHOP_SESSION (speaker_id);
alter table WORKSHOP_SESSION add constraint fk_WORKSHOP_SESSION_workshop_7 foreign key (workshop_id) references WORKSHOP (id) on delete restrict on update restrict;
create index ix_WORKSHOP_SESSION_workshop_7 on WORKSHOP_SESSION (workshop_id);



alter table WORKSHOP_SPEAKERS add constraint fk_WORKSHOP_SPEAKERS_WORKSHOP_01 foreign key (workshop_id) references WORKSHOP (id) on delete restrict on update restrict;

alter table WORKSHOP_SPEAKERS add constraint fk_WORKSHOP_SPEAKERS_USER_02 foreign key (user_id) references USER (id) on delete restrict on update restrict;

alter table POTENTIAL_PARTICIPANTS add constraint fk_POTENTIAL_PARTICIPANTS_WOR_01 foreign key (workshop_id) references WORKSHOP (id) on delete restrict on update restrict;

alter table POTENTIAL_PARTICIPANTS add constraint fk_POTENTIAL_PARTICIPANTS_USE_02 foreign key (user_id) references USER (id) on delete restrict on update restrict;

alter table PARTICIPANTS add constraint fk_PARTICIPANTS_WORKSHOP_SESS_01 foreign key (workshop_id) references WORKSHOP_SESSION (id) on delete restrict on update restrict;

alter table PARTICIPANTS add constraint fk_PARTICIPANTS_USER_02 foreign key (user_id) references USER (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists ACTION;

drop table if exists COMMENT;

drop table if exists RESSOURCE;

drop table if exists USER;

drop table if exists WORKSHOP;

drop table if exists WORKSHOP_SPEAKERS;

drop table if exists POTENTIAL_PARTICIPANTS;

drop table if exists WORKSHOP_SESSION;

drop table if exists PARTICIPANTS;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists ACTION_seq;

drop sequence if exists COMMENT_seq;

drop sequence if exists RESSOURCE_seq;

drop sequence if exists USER_seq;

drop sequence if exists WORKSHOP_seq;

drop sequence if exists WORKSHOP_SESSION_seq;

