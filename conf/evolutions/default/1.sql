# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table Carrera (
  id                        bigint auto_increment not null,
  nombre                    varchar(255),
  fecha_registro            datetime,
  facultad_id               bigint,
  universidad_id            bigint,
  constraint pk_Carrera primary key (id))
;

create table Carrera_Curso (
  carrera_id                bigint,
  curso_id                  bigint)
;

create table Curso (
  id                        bigint auto_increment not null,
  codigo                    varchar(255),
  nombre                    varchar(255),
  constraint uq_Curso_codigo unique (codigo),
  constraint pk_Curso primary key (id))
;

create table Curso_Usuario (
  seccion                   integer,
  usuario_id                bigint,
  curso_id                  bigint)
;

create table Enlace (
  id                        bigint auto_increment not null,
  url                       varchar(255),
  descripcion               varchar(255),
  imagen                    varchar(255),
  titulo                    varchar(255),
  emisor_id                 bigint,
  curso_id                  bigint,
  constraint pk_Enlace primary key (id))
;

create table Facultad (
  id                        bigint auto_increment not null,
  nombre                    varchar(255),
  fecha_registro            datetime,
  universidad_id            bigint,
  constraint pk_Facultad primary key (id))
;

create table Mensaje (
  id                        bigint auto_increment not null,
  titulo                    varchar(255),
  contenido                 varchar(4000) not null,
  tipo                      integer,
  fecha                     datetime,
  mensaje_id                bigint,
  emisor_id                 bigint,
  seccion_id                bigint,
  facultad_id               bigint,
  universidad_id            bigint,
  carrera_id                bigint,
  constraint pk_Mensaje primary key (id))
;

create table Mensaje_Receptor (
  mensaje_id                bigint,
  receptor_id               bigint)
;

create table Seccion (
  id                        bigint auto_increment not null,
  seccion                   integer,
  profesor_id               bigint,
  curso_id                  bigint,
  constraint pk_Seccion primary key (id))
;

create table Seccion_Usuario (
  usuario_id                bigint,
  seccion_id                bigint)
;

create table Universidad (
  id                        bigint auto_increment not null,
  nombre                    varchar(255),
  fecha_registro            datetime,
  constraint pk_Universidad primary key (id))
;

create table Usuario (
  id                        bigint auto_increment not null,
  username                  varchar(255) not null,
  password                  varchar(255) not null,
  nombres                   varchar(255),
  apellidos                 varchar(255),
  email                     varchar(255),
  privilegio                Integer default '0' not null,
  rol                       Integer default '0' not null,
  universidad_id            bigint,
  carrera_id                bigint,
  facultad_id               bigint,
  constraint uq_Usuario_username unique (username),
  constraint pk_Usuario primary key (id))
;

alter table Carrera add constraint fk_Carrera_facultad_1 foreign key (facultad_id) references Facultad (id) on delete restrict on update restrict;
create index ix_Carrera_facultad_1 on Carrera (facultad_id);
alter table Carrera add constraint fk_Carrera_universidad_2 foreign key (universidad_id) references Universidad (id) on delete restrict on update restrict;
create index ix_Carrera_universidad_2 on Carrera (universidad_id);
alter table Carrera_Curso add constraint fk_Carrera_Curso_carrera_3 foreign key (carrera_id) references Carrera (id) on delete restrict on update restrict;
create index ix_Carrera_Curso_carrera_3 on Carrera_Curso (carrera_id);
alter table Carrera_Curso add constraint fk_Carrera_Curso_curso_4 foreign key (curso_id) references Curso (id) on delete restrict on update restrict;
create index ix_Carrera_Curso_curso_4 on Carrera_Curso (curso_id);
alter table Curso_Usuario add constraint fk_Curso_Usuario_usuario_5 foreign key (usuario_id) references Usuario (id) on delete restrict on update restrict;
create index ix_Curso_Usuario_usuario_5 on Curso_Usuario (usuario_id);
alter table Curso_Usuario add constraint fk_Curso_Usuario_curso_6 foreign key (curso_id) references Curso (id) on delete restrict on update restrict;
create index ix_Curso_Usuario_curso_6 on Curso_Usuario (curso_id);
alter table Enlace add constraint fk_Enlace_emisor_7 foreign key (emisor_id) references Usuario (id) on delete restrict on update restrict;
create index ix_Enlace_emisor_7 on Enlace (emisor_id);
alter table Enlace add constraint fk_Enlace_curso_8 foreign key (curso_id) references Curso (id) on delete restrict on update restrict;
create index ix_Enlace_curso_8 on Enlace (curso_id);
alter table Facultad add constraint fk_Facultad_universidad_9 foreign key (universidad_id) references Universidad (id) on delete restrict on update restrict;
create index ix_Facultad_universidad_9 on Facultad (universidad_id);
alter table Mensaje add constraint fk_Mensaje_mensaje_10 foreign key (mensaje_id) references Mensaje (id) on delete restrict on update restrict;
create index ix_Mensaje_mensaje_10 on Mensaje (mensaje_id);
alter table Mensaje add constraint fk_Mensaje_emisor_11 foreign key (emisor_id) references Usuario (id) on delete restrict on update restrict;
create index ix_Mensaje_emisor_11 on Mensaje (emisor_id);
alter table Mensaje add constraint fk_Mensaje_seccion_12 foreign key (seccion_id) references Seccion (id) on delete restrict on update restrict;
create index ix_Mensaje_seccion_12 on Mensaje (seccion_id);
alter table Mensaje add constraint fk_Mensaje_facultad_13 foreign key (facultad_id) references Facultad (id) on delete restrict on update restrict;
create index ix_Mensaje_facultad_13 on Mensaje (facultad_id);
alter table Mensaje add constraint fk_Mensaje_universidad_14 foreign key (universidad_id) references Universidad (id) on delete restrict on update restrict;
create index ix_Mensaje_universidad_14 on Mensaje (universidad_id);
alter table Mensaje add constraint fk_Mensaje_carrera_15 foreign key (carrera_id) references Carrera (id) on delete restrict on update restrict;
create index ix_Mensaje_carrera_15 on Mensaje (carrera_id);
alter table Mensaje_Receptor add constraint fk_Mensaje_Receptor_mensaje_16 foreign key (mensaje_id) references Mensaje (id) on delete restrict on update restrict;
create index ix_Mensaje_Receptor_mensaje_16 on Mensaje_Receptor (mensaje_id);
alter table Mensaje_Receptor add constraint fk_Mensaje_Receptor_receptor_17 foreign key (receptor_id) references Usuario (id) on delete restrict on update restrict;
create index ix_Mensaje_Receptor_receptor_17 on Mensaje_Receptor (receptor_id);
alter table Seccion add constraint fk_Seccion_profesor_18 foreign key (profesor_id) references Usuario (id) on delete restrict on update restrict;
create index ix_Seccion_profesor_18 on Seccion (profesor_id);
alter table Seccion add constraint fk_Seccion_curso_19 foreign key (curso_id) references Curso (id) on delete restrict on update restrict;
create index ix_Seccion_curso_19 on Seccion (curso_id);
alter table Seccion_Usuario add constraint fk_Seccion_Usuario_usuario_20 foreign key (usuario_id) references Usuario (id) on delete restrict on update restrict;
create index ix_Seccion_Usuario_usuario_20 on Seccion_Usuario (usuario_id);
alter table Seccion_Usuario add constraint fk_Seccion_Usuario_seccion_21 foreign key (seccion_id) references Seccion (id) on delete restrict on update restrict;
create index ix_Seccion_Usuario_seccion_21 on Seccion_Usuario (seccion_id);
alter table Usuario add constraint fk_Usuario_universidad_22 foreign key (universidad_id) references Universidad (id) on delete restrict on update restrict;
create index ix_Usuario_universidad_22 on Usuario (universidad_id);
alter table Usuario add constraint fk_Usuario_carrera_23 foreign key (carrera_id) references Carrera (id) on delete restrict on update restrict;
create index ix_Usuario_carrera_23 on Usuario (carrera_id);
alter table Usuario add constraint fk_Usuario_facultad_24 foreign key (facultad_id) references Facultad (id) on delete restrict on update restrict;
create index ix_Usuario_facultad_24 on Usuario (facultad_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table Carrera;

drop table Carrera_Curso;

drop table Curso;

drop table Curso_Usuario;

drop table Enlace;

drop table Facultad;

drop table Mensaje;

drop table Mensaje_Receptor;

drop table Seccion;

drop table Seccion_Usuario;

drop table Universidad;

drop table Usuario;

SET FOREIGN_KEY_CHECKS=1;

