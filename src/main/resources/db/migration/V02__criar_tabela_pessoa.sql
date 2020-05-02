create table pessoa(
	codigo bigint(20) auto_increment primary key,
	nome varchar(50) not null,
	logradouro varchar(50),
	numero varchar(6),
	complemento varchar(50),
	bairro varchar(10),
	cep char(10),
	cidade varchar(30),
	estado char(2),
	ativo boolean
)engine=InnoDB default charset=utf8;

insert pessoa(nome,logradouro,numero,complemento,bairro,cep,cidade,estado,ativo) values
	('Jandeilson','Rua manoel alves','314','Em frente ao colegio', 'centro','58.210-000','pilõezinhos','PB',true);
insert pessoa(nome,logradouro,numero,complemento,bairro,cep,cidade,estado,ativo) values
	('Maria','Rua manoel alves','314','Em frente ao colegio', 'centro','58.210-000','pilõezinhos','PB',true);
insert pessoa(nome,logradouro,numero,complemento,bairro,cep,cidade,estado,ativo) values
	('Francisco','Rua manoel alves','314','Em frente ao colegio', 'centro','58.210-000','pilõezinhos','PB',true);
insert pessoa(nome,ativo) values ('jandeilson',false);
insert pessoa(nome,ativo) values ('João',false);
insert pessoa(nome,ativo) values ('Pedrinho',false);
	