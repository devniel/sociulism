# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table user (
  uid                       bigint auto_increment not null,
  codigo                    varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (uid))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

