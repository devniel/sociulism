use sociul;

select * from usuario;
select * from universidad;
select * from facultad;
select * from carrera;

alter table Curso_Usuario drop foreign key fk_Curso_Usuario_usuario_5;
alter table Curso_Usuario add constraint fk_Curso_Usuario_usuario_5 foreign key (usuario_id) references Usuario (id) on delete cascade on update restrict;






select * from curso_usuario;

describe usuario;

show tables;

select * from curso;

select * from curso_usuario;

insert into usuario (username,password,nombres,apellidos,privilegio,rol) values("admin","123","Administrador","Ulima",3,3);
insert into universidad (nombre) values ("UNIVERSIDAD DE LIMA");

select * from usuario where apellidos like "%DEL MAR%" and nombres like "%RONNIE%";


alter table Carrera drop foreign key fk_Carrera_facultad_1;
alter table Carrera add constraint fk_Carrera_facultad_1 foreign key (facultad_id) references Facultad (id) on delete cascade on update no action;

alter table Carrera drop foreign key fk_Carrera_universidad_2;
alter table Carrera add constraint fk_Carrera_universidad_2 foreign key (universidad_id) references Universidad (id) on delete cascade on update no action;

alter table Carrera_Curso drop foreign key fk_Carrera_Curso_carrera_3;
alter table Carrera_Curso add constraint fk_Carrera_Curso_carrera_3 foreign key (carrera_id) references Carrera (id) on delete cascade on update no action;

alter table Carrera_Curso drop foreign key fk_Carrera_Curso_curso_4;
alter table Carrera_Curso add constraint fk_Carrera_Curso_curso_4 foreign key (curso_id) references Curso (id) on delete cascade on update no action;

alter table Curso_Usuario drop foreign key fk_Curso_Usuario_usuario_5;
alter table Curso_Usuario add constraint fk_Curso_Usuario_usuario_5 foreign key (usuario_id) references Usuario (id) on delete cascade on update no action;

alter table Curso_Usuario drop foreign key fk_Curso_Usuario_curso_6;
alter table Curso_Usuario add constraint fk_Curso_Usuario_curso_6 foreign key (curso_id) references Curso (id) on delete cascade on update no action;

alter table Enlace drop foreign key fk_Enlace_emisor_7;
alter table Enlace add constraint fk_Enlace_emisor_7 foreign key (emisor_id) references Usuario (id) on delete cascade on update no action;

alter table Enlace drop foreign key fk_Enlace_curso_8;
alter table Enlace add constraint fk_Enlace_curso_8 foreign key (curso_id) references Curso (id) on delete cascade on update no action;

alter table Facultad drop foreign key fk_Facultad_universidad_9;
alter table Facultad add constraint fk_Facultad_universidad_9 foreign key (universidad_id) references Universidad (id) on delete cascade on update no action;

alter table Mensaje drop foreign key fk_Mensaje_mensaje_10;
alter table Mensaje add constraint fk_Mensaje_mensaje_10 foreign key (mensaje_id) references Mensaje (id) on delete cascade on update no action;

alter table Mensaje drop foreign key fk_Mensaje_emisor_11;
alter table Mensaje add constraint fk_Mensaje_emisor_11 foreign key (emisor_id) references Usuario (id) on delete cascade on update no action;

alter table Mensaje drop foreign key fk_Mensaje_curso_12;
alter table Mensaje add constraint fk_Mensaje_curso_12 foreign key (curso_id) references Curso (id) on delete cascade on update no action;

alter table Mensaje drop foreign key fk_Mensaje_facultad_13;
alter table Mensaje add constraint fk_Mensaje_facultad_13 foreign key (facultad_id) references Facultad (id) on delete cascade on update no action;

alter table Mensaje drop foreign key fk_Mensaje_universidad_14;
alter table Mensaje add constraint fk_Mensaje_universidad_14 foreign key (universidad_id) references Universidad (id) on delete cascade on update no action;

alter table Mensaje drop foreign key fk_Mensaje_carrera_15;
alter table Mensaje add constraint fk_Mensaje_carrera_15 foreign key (carrera_id) references Carrera (id) on delete cascade on update no action;

alter table Mensaje_Receptor drop foreign key fk_Mensaje_Receptor_mensaje_16;
alter table Mensaje_Receptor add constraint fk_Mensaje_Receptor_mensaje_16 foreign key (mensaje_id) references Mensaje (id) on delete cascade on update no action;

alter table Mensaje_Receptor drop foreign key fk_Mensaje_Receptor_receptor_17;
alter table Mensaje_Receptor add constraint fk_Mensaje_Receptor_receptor_17 foreign key (receptor_id) references Usuario (id) on delete cascade on update no action;

alter table Usuario drop foreign key fk_Usuario_universidad_18;
alter table Usuario add constraint fk_Usuario_universidad_18 foreign key (universidad_id) references Universidad (id) on delete cascade on update no action;

alter table Usuario drop foreign key fk_Usuario_carrera_19;
alter table Usuario add constraint fk_Usuario_carrera_19 foreign key (carrera_id) references Carrera (id) on delete cascade on update no action;

alter table Usuario drop foreign key fk_Usuario_facultad_20;
alter table Usuario add constraint fk_Usuario_facultad_20 foreign key (facultad_id) references Facultad (id) on delete cascade on update no action;


