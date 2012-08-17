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
insert into universidad (nombre) values ("Universidad de Lima");


select * from usuario where apellidos like "%DEL MAR%" and nombres like "%RONNIE%";

