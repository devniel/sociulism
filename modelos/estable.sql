use sociul;

select * from usuario;
select * from universidad;
select * from facultad;
select * from carrera;

alter table Curso_Usuario add constraint fk_Curso_Usuario_usuario_5 foreign key (usuario_uid) references Usuario (uid) on delete cascade on update restrict;

select* from usuario;

describe usuario;

insert into usuario (username,password,nombres,apellidos,privilegio,rol) values("admin","123","Administrador","Ulima",3,3);
insert into universidad (nombre) values ("Universidad de Lima");

