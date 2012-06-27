# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table Curso (
  cid                       bigint auto_increment not null,
  codigo                    varchar(255),
  nombre                    varchar(255),
  constraint uq_Curso_codigo unique (codigo),
  constraint pk_Curso primary key (cid))
;

create table Curso_Usuario (
  seccion                   integer,
  usuario_uid               bigint,
  curso_cid                 bigint)
;

create table Enlace (
  url                       varchar(255),
  descripcion               varchar(255),
  imagen                    varchar(255),
  titulo                    varchar(255),
  emisor_uid                bigint,
  curso_cid                 bigint)
;

create table Mensaje (
  mid                       bigint auto_increment not null,
  contenido                 varchar(255),
  fecha                     datetime,
  emisor_uid                bigint,
  curso_cid                 bigint,
  constraint pk_Mensaje primary key (mid))
;

create table Usuario (
  uid                       bigint auto_increment not null,
  codigo                    varchar(255),
  password                  varchar(255),
  nombres                   varchar(255),
  apellidos                 varchar(255),
  constraint uq_Usuario_codigo unique (codigo),
  constraint pk_Usuario primary key (uid))
;

alter table Curso_Usuario add constraint fk_Curso_Usuario_usuario_1 foreign key (usuario_uid) references Usuario (uid) on delete restrict on update restrict;
create index ix_Curso_Usuario_usuario_1 on Curso_Usuario (usuario_uid);
alter table Curso_Usuario add constraint fk_Curso_Usuario_curso_2 foreign key (curso_cid) references Curso (cid) on delete restrict on update restrict;
create index ix_Curso_Usuario_curso_2 on Curso_Usuario (curso_cid);
alter table Enlace add constraint fk_Enlace_emisor_3 foreign key (emisor_uid) references Usuario (uid) on delete restrict on update restrict;
create index ix_Enlace_emisor_3 on Enlace (emisor_uid);
alter table Enlace add constraint fk_Enlace_curso_4 foreign key (curso_cid) references Curso (cid) on delete restrict on update restrict;
create index ix_Enlace_curso_4 on Enlace (curso_cid);
alter table Mensaje add constraint fk_Mensaje_emisor_5 foreign key (emisor_uid) references Usuario (uid) on delete restrict on update restrict;
create index ix_Mensaje_emisor_5 on Mensaje (emisor_uid);
alter table Mensaje add constraint fk_Mensaje_curso_6 foreign key (curso_cid) references Curso (cid) on delete restrict on update restrict;
create index ix_Mensaje_curso_6 on Mensaje (curso_cid);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table Curso;

drop table Curso_Usuario;

drop table Enlace;

drop table Mensaje;

drop table Usuario;

SET FOREIGN_KEY_CHECKS=1;

