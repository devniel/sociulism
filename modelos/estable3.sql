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

alter table Facultad drop foreign key fk_Facultad_universidad_7; 
alter table Facultad add constraint fk_Facultad_universidad_7 foreign key (universidad_id) references Universidad (id) on delete cascade on update restrict;

alter table Mensaje drop foreign key fk_Mensaje_mensaje_8; 
alter table Mensaje add constraint fk_Mensaje_mensaje_8 foreign key (mensaje_id) references Mensaje (id) on delete cascade on update restrict;

alter table Mensaje drop foreign key fk_Mensaje_emisor_9; 
alter table Mensaje add constraint fk_Mensaje_emisor_9 foreign key (emisor_id) references Usuario (id) on delete cascade on update restrict;

alter table Carrera drop foreign key fk_Carrera_facultad_1; 
alter table Mensaje add constraint fk_Mensaje_seccion_10 foreign key (seccion_id) references Seccion (id) on delete cascade on update restrict;

alter table Mensaje drop foreign key fk_Mensaje_facultad_11; 
alter table Mensaje add constraint fk_Mensaje_facultad_11 foreign key (facultad_id) references Facultad (id) on delete cascade on update restrict;

alter table Mensaje drop foreign key fk_Mensaje_universidad_12; 
alter table Mensaje add constraint fk_Mensaje_universidad_12 foreign key (universidad_id) references Universidad (id) on delete cascade on update restrict;

alter table Mensaje drop foreign key fk_Mensaje_carrera_13; 
alter table Mensaje add constraint fk_Mensaje_carrera_13 foreign key (carrera_id) references Carrera (id) on delete cascade on update restrict;

alter table Mensaje_Receptor drop foreign key fk_Mensaje_Receptor_mensaje_14; 
alter table Mensaje_Receptor add constraint fk_Mensaje_Receptor_mensaje_14 foreign key (mensaje_id) references Mensaje (id) on delete cascade on update restrict;

alter table Mensaje_Receptor drop foreign key fk_Mensaje_Receptor_receptor_15; 
alter table Mensaje_Receptor add constraint fk_Mensaje_Receptor_receptor_15 foreign key (receptor_id) references Usuario (id) on delete cascade on update restrict;

alter table Seccion drop foreign key fk_Seccion_profesor_16; 
alter table Seccion add constraint fk_Seccion_profesor_16 foreign key (profesor_id) references Usuario (id) on delete cascade on update restrict;

alter table Seccion drop foreign key fk_Seccion_curso_17; 
alter table Seccion add constraint fk_Seccion_curso_17 foreign key (curso_id) references Curso (id) on delete cascade on update restrict;

alter table Seccion_Usuario drop foreign key fk_Seccion_Usuario_usuario_18; 
alter table Seccion_Usuario add constraint fk_Seccion_Usuario_usuario_18 foreign key (usuario_id) references Usuario (id) on delete cascade on update restrict;

alter table Seccion_Usuario drop foreign key fk_Seccion_Usuario_seccion_19; 
alter table Seccion_Usuario add constraint fk_Seccion_Usuario_seccion_19 foreign key (seccion_id) references Seccion (id) on delete cascade on update restrict;

alter table Usuario drop foreign key fk_Usuario_universidad_20; 
alter table Usuario add constraint fk_Usuario_universidad_20 foreign key (universidad_id) references Universidad (id) on delete cascade on update restrict;

alter table Usuario drop foreign key fk_Usuario_carrera_21; 
alter table Usuario add constraint fk_Usuario_carrera_21 foreign key (carrera_id) references Carrera (id) on delete cascade on update restrict;

alter table Usuario drop foreign key fk_Usuario_facultad_22; 
alter table Usuario add constraint fk_Usuario_facultad_22 foreign key (facultad_id) references Facultad (id) on delete cascade on update restrict;

