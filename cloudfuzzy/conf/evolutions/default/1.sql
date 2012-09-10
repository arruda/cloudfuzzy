# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table fuzzy_system (
  id                        bigint not null,
  name                      varchar(255),
  file_name                 varchar(255),
  file_path                 varchar(255),
  user_email                varchar(255),
  constraint pk_fuzzy_system primary key (id))
;

create table user (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (email))
;

create sequence fuzzy_system_seq;

create sequence user_seq;

alter table fuzzy_system add constraint fk_fuzzy_system_user_1 foreign key (user_email) references user (email) on delete restrict on update restrict;
create index ix_fuzzy_system_user_1 on fuzzy_system (user_email);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists fuzzy_system;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists fuzzy_system_seq;

drop sequence if exists user_seq;

