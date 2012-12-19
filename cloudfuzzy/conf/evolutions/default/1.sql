# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table fuzzy_system (
  id                        bigint not null,
  name                      varchar(255),
  description               varchar(255),
  file_name                 varchar(255),
  file_path                 varchar(255),
  user_email                varchar(255),
  constraint pk_fuzzy_system primary key (id))
;

create table rule_base_call (
  id                        bigint not null,
  position                  integer,
  x_pos                     double,
  y_pos                     double,
  system_id                 bigint,
  constraint pk_rule_base_call primary key (id))
;

create table user (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (email))
;

create sequence fuzzy_system_seq;

create sequence rule_base_call_seq;

create sequence user_seq;

alter table fuzzy_system add constraint fk_fuzzy_system_user_1 foreign key (user_email) references user (email) on delete restrict on update restrict;
create index ix_fuzzy_system_user_1 on fuzzy_system (user_email);
alter table rule_base_call add constraint fk_rule_base_call_system_2 foreign key (system_id) references fuzzy_system (id) on delete restrict on update restrict;
create index ix_rule_base_call_system_2 on rule_base_call (system_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists fuzzy_system;

drop table if exists rule_base_call;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists fuzzy_system_seq;

drop sequence if exists rule_base_call_seq;

drop sequence if exists user_seq;

