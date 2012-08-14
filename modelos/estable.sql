/*select * from usuario;
select * from universidad;
select * from facultad;
select * from carrera;*/

alter table Curso_Usuario add constraint fk_Curso_Usuario_usuario_5 foreign key (usuario_uid) references Usuario (uid) on delete cascade on update restrict;
