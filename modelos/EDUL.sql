alter table Carrera drop foreign key fk_Carrera_facultad_1;
alter table Carrera add constraint fk_Carrera_facultad_1 foreign key (facultad_id) references Facultad (id) on delete cascade on update restrict;

alter table Carrera drop foreign key fk_Carrera_universidad_2;
alter table Carrera add constraint fk_Carrera_universidad_2 foreign key (universidad_id) references Universidad (id) on delete cascade on update restrict;

alter table Carrera_Curso drop foreign key fk_Carrera_Curso_carrera_3;
alter table Carrera_Curso add constraint fk_Carrera_Curso_carrera_3 foreign key (carrera_id) references Carrera (id) on delete cascade on update restrict;

alter table Carrera_Curso drop foreign key fk_Carrera_Curso_curso_4;
alter table Carrera_Curso add constraint fk_Carrera_Curso_curso_4 foreign key (curso_id) references Curso (id) on delete cascade on update restrict;

alter table Curso_Usuario drop foreign key fk_Curso_Usuario_usuario_5;
alter table Curso_Usuario add constraint fk_Curso_Usuario_usuario_5 foreign key (usuario_id) references Usuario (id) on delete cascade on update restrict;

alter table Curso_Usuario drop foreign key fk_Curso_Usuario_curso_6;
alter table Curso_Usuario add constraint fk_Curso_Usuario_curso_6 foreign key (curso_id) references Curso (id) on delete cascade on update restrict;

alter table Enlace drop foreign key fk_Enlace_emisor_7;
alter table Enlace add constraint fk_Enlace_emisor_7 foreign key (emisor_id) references Usuario (id) on delete cascade on update restrict;

alter table Enlace drop foreign key fk_Enlace_curso_8;
alter table Enlace add constraint fk_Enlace_curso_8 foreign key (curso_id) references Curso (id) on delete cascade on update restrict;

alter table Facultad drop foreign key fk_Facultad_universidad_9;
alter table Facultad add constraint fk_Facultad_universidad_9 foreign key (universidad_id) references Universidad (id) on delete cascade on update restrict;

alter table Mensaje drop foreign key fk_Mensaje_mensaje_10;
alter table Mensaje add constraint fk_Mensaje_mensaje_10 foreign key (mensaje_id) references Mensaje (id) on delete cascade on update restrict;

alter table Mensaje drop foreign key fk_Mensaje_emisor_11;
alter table Mensaje add constraint fk_Mensaje_emisor_11 foreign key (emisor_id) references Usuario (id) on delete cascade on update restrict;

alter table Mensaje drop foreign key fk_Mensaje_seccion_12;
alter table Mensaje add constraint fk_Mensaje_seccion_12 foreign key (seccion_id) references Seccion (id) on delete cascade on update restrict;

alter table Mensaje drop foreign key fk_Mensaje_facultad_13;
alter table Mensaje add constraint fk_Mensaje_facultad_13 foreign key (facultad_id) references Facultad (id) on delete cascade on update restrict;

alter table Mensaje drop foreign key fk_Mensaje_universidad_14;
alter table Mensaje add constraint fk_Mensaje_universidad_14 foreign key (universidad_id) references Universidad (id) on delete cascade on update restrict;

alter table Mensaje drop foreign key fk_Mensaje_carrera_15;
alter table Mensaje add constraint fk_Mensaje_carrera_15 foreign key (carrera_id) references Carrera (id) on delete cascade on update restrict;

alter table Mensaje_Receptor drop foreign key fk_Mensaje_Receptor_mensaje_16;
alter table Mensaje_Receptor add constraint fk_Mensaje_Receptor_mensaje_16 foreign key (mensaje_id) references Mensaje (id) on delete cascade on update restrict;

alter table Mensaje_Receptor drop foreign key fk_Mensaje_Receptor_receptor_17;
alter table Mensaje_Receptor add constraint fk_Mensaje_Receptor_receptor_17 foreign key (receptor_id) references Usuario (id) on delete cascade on update restrict;

alter table Seccion drop foreign key fk_Seccion_profesor_18;
alter table Seccion add constraint fk_Seccion_profesor_18 foreign key (profesor_id) references Usuario (id) on delete cascade on update restrict;

alter table Seccion drop foreign key fk_Seccion_curso_19;
alter table Seccion add constraint fk_Seccion_curso_19 foreign key (curso_id) references Curso (id) on delete cascade on update restrict;

alter table Seccion_Usuario drop foreign key fk_Seccion_Usuario_usuario_20;
alter table Seccion_Usuario add constraint fk_Seccion_Usuario_usuario_20 foreign key (usuario_id) references Usuario (id) on delete cascade on update restrict;

alter table Seccion_Usuario drop foreign key fk_Seccion_Usuario_seccion_21;
alter table Seccion_Usuario add constraint fk_Seccion_Usuario_seccion_21 foreign key (seccion_id) references Seccion (id) on delete cascade on update restrict;

alter table Usuario drop foreign key fk_Usuario_universidad_22;
alter table Usuario add constraint fk_Usuario_universidad_22 foreign key (universidad_id) references Universidad (id) on delete cascade on update restrict;

alter table Usuario drop foreign key fk_Usuario_carrera_23;
alter table Usuario add constraint fk_Usuario_carrera_23 foreign key (carrera_id) references Carrera (id) on delete cascade on update restrict;

alter table Usuario drop foreign key fk_Usuario_facultad_24;
alter table Usuario add constraint fk_Usuario_facultad_24 foreign key (facultad_id) references Facultad (id) on delete cascade on update restrict;


alter table Usuario_Asesor drop foreign key fk_Usuario_Asesor_usuario_25;
alter table Usuario_Asesor add constraint fk_Usuario_Asesor_usuario_25 foreign key (usuario_id) references Usuario (id) on delete cascade on update restrict;

alter table Usuario_Asesor drop foreign key fk_Usuario_Asesor_asesor_26;
alter table Usuario_Asesor add constraint fk_Usuario_Asesor_asesor_26 foreign key (asesor_id) references Usuario (id) on delete cascade on update restrict;


select * from facultad;

insert into universidad(nombre) values("Universidad de Lima");
insert into facultad(nombre,universidad_id) values("Facultad de Ingeniería de Sistemas",1);
insert into usuario (username,password,nombres,apellidos,email,privilegio,rol,universidad_id,facultad_id) values("admin","dni007","Daniel Mauricio","Flores Sánchez","dnielfs@gmail.com",2,2,1,1);
