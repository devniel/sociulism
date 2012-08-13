# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table Carrera (
  id                        bigint auto_increment not null,
  nombre                    varchar(255),
  fecha_registro            datetime,
  facultad_id               bigint,
  constraint pk_Carrera primary key (id))
;

create table Carrera_Curso (
  carrera_id                bigint,
  curso_cid                 bigint)
;

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
  eid                       bigint auto_increment not null,
  url                       varchar(255),
  descripcion               varchar(255),
  imagen                    varchar(255),
  titulo                    varchar(255),
  emisor_uid                bigint,
  curso_cid                 bigint,
  constraint pk_Enlace primary key (eid))
;

create table Facultad (
  id                        bigint auto_increment not null,
  nombre                    varchar(255),
  fecha_registro            datetime,
  universidad_id            bigint,
  constraint pk_Facultad primary key (id))
;

create table Mensaje (
  mid                       bigint auto_increment not null,
  contenido                 varchar(255),
  fecha                     datetime,
  emisor_uid                bigint,
  receptor_uid              bigint,
  curso_cid                 bigint,
  facultad_id               bigint,
  universidad_id            bigint,
  carrera_id                bigint,
  constraint pk_Mensaje primary key (mid))
;

create table Universidad (
  id                        bigint auto_increment not null,
  nombre                    varchar(255),
  fecha_registro            datetime,
  constraint pk_Universidad primary key (id))
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

alter table Carrera add constraint fk_Carrera_facultad_1 foreign key (facultad_id) references Facultad (id) on delete restrict on update restrict;
create index ix_Carrera_facultad_1 on Carrera (facultad_id);
alter table Carrera_Curso add constraint fk_Carrera_Curso_carrera_2 foreign key (carrera_id) references Carrera (id) on delete restrict on update restrict;
create index ix_Carrera_Curso_carrera_2 on Carrera_Curso (carrera_id);
alter table Carrera_Curso add constraint fk_Carrera_Curso_curso_3 foreign key (curso_cid) references Curso (cid) on delete restrict on update restrict;
create index ix_Carrera_Curso_curso_3 on Carrera_Curso (curso_cid);
alter table Curso_Usuario add constraint fk_Curso_Usuario_usuario_4 foreign key (usuario_uid) references Usuario (uid) on delete restrict on update restrict;
create index ix_Curso_Usuario_usuario_4 on Curso_Usuario (usuario_uid);
alter table Curso_Usuario add constraint fk_Curso_Usuario_curso_5 foreign key (curso_cid) references Curso (cid) on delete restrict on update restrict;
create index ix_Curso_Usuario_curso_5 on Curso_Usuario (curso_cid);
alter table Enlace add constraint fk_Enlace_emisor_6 foreign key (emisor_uid) references Usuario (uid) on delete restrict on update restrict;
create index ix_Enlace_emisor_6 on Enlace (emisor_uid);
alter table Enlace add constraint fk_Enlace_curso_7 foreign key (curso_cid) references Curso (cid) on delete restrict on update restrict;
create index ix_Enlace_curso_7 on Enlace (curso_cid);
alter table Facultad add constraint fk_Facultad_universidad_8 foreign key (universidad_id) references Universidad (id) on delete restrict on update restrict;
create index ix_Facultad_universidad_8 on Facultad (universidad_id);
alter table Mensaje add constraint fk_Mensaje_emisor_9 foreign key (emisor_uid) references Usuario (uid) on delete restrict on update restrict;
create index ix_Mensaje_emisor_9 on Mensaje (emisor_uid);
alter table Mensaje add constraint fk_Mensaje_receptor_10 foreign key (receptor_uid) references Usuario (uid) on delete restrict on update restrict;
create index ix_Mensaje_receptor_10 on Mensaje (receptor_uid);
alter table Mensaje add constraint fk_Mensaje_curso_11 foreign key (curso_cid) references Curso (cid) on delete restrict on update restrict;
create index ix_Mensaje_curso_11 on Mensaje (curso_cid);
alter table Mensaje add constraint fk_Mensaje_facultad_12 foreign key (facultad_id) references Facultad (id) on delete restrict on update restrict;
create index ix_Mensaje_facultad_12 on Mensaje (facultad_id);
alter table Mensaje add constraint fk_Mensaje_universidad_13 foreign key (universidad_id) references Universidad (id) on delete restrict on update restrict;
create index ix_Mensaje_universidad_13 on Mensaje (universidad_id);
alter table Mensaje add constraint fk_Mensaje_carrera_14 foreign key (carrera_id) references Carrera (id) on delete restrict on update restrict;
create index ix_Mensaje_carrera_14 on Mensaje (carrera_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table Carrera;

drop table Carrera_Curso;

drop table Curso;

drop table Curso_Usuario;

drop table Enlace;

drop table Facultad;

drop table Mensaje;

drop table Universidad;

drop table Usuario;

SET FOREIGN_KEY_CHECKS=1;

