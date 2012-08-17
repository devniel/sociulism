
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

alter table Mensaje drop foreign key fk_Mensaje_emisor_10;
alter table Mensaje add constraint fk_Mensaje_emisor_10 foreign key (emisor_id) references Usuario (id) on delete cascade on update no action;

alter table Mensaje drop foreign key fk_Mensaje_receptor_11;
alter table Mensaje add constraint fk_Mensaje_receptor_11 foreign key (receptor_id) references Usuario (id) on delete cascade on update no action;

alter table Mensaje drop foreign key fk_Mensaje_curso_12;
alter table Mensaje add constraint fk_Mensaje_curso_12 foreign key (curso_id) references Curso (id) on delete cascade on update no action;

alter table Mensaje drop foreign key fk_Mensaje_facultad_13;
alter table Mensaje add constraint fk_Mensaje_facultad_13 foreign key (facultad_id) references Facultad (id) on delete cascade on update no action;

alter table Mensaje drop foreign key fk_Mensaje_universidad_14;
alter table Mensaje add constraint fk_Mensaje_universidad_14 foreign key (universidad_id) references Universidad (id) on delete cascade on update no action;

alter table Mensaje drop foreign key fk_Mensaje_carrera_15;
alter table Mensaje add constraint fk_Mensaje_carrera_15 foreign key (carrera_id) references Carrera (id) on delete cascade on update no action;

alter table Usuario drop foreign key fk_Usuario_universidad_16;
alter table Usuario add constraint fk_Usuario_universidad_16 foreign key (universidad_id) references Universidad (id) on delete cascade on update no action;

alter table Usuario drop foreign key fk_Usuario_carrera_17 ;
alter table Usuario add constraint fk_Usuario_carrera_17 foreign key (carrera_id) references Carrera (id) on delete cascade on update no action;

alter table Usuario drop foreign key fk_Usuario_facultad_18;
alter table Usuario add constraint fk_Usuario_facultad_18 foreign key (facultad_id) references Facultad (id) on delete cascade on update no action;

