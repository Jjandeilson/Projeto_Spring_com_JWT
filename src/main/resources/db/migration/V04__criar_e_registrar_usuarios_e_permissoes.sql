create table usuario(
	codigo bigint(20) primary key,
	nome varchar(50) not null,
	email varchar(50) not null,
	senha varchar(150) not null
)engine=InnoDB default charset=utf8;

create table permissao(
	codigo bigint(20) primary key,
	descricao varchar(50) not null
)engine=InnoDB default charset=utf8;

create table usuario_permissao(
	codigo_usuario bigint(20) not null,
	codigo_permissao bigint(20) not null,
	primary key (codigo_usuario, codigo_permissao),
	foreign key (codigo_usuario) references usuario(codigo),
	foreign key (codigo_permissao) references permissao(codigo)
)engine=InnoDB default charset=utf8;

insert into usuario (codigo, nome, email, senha) values (1, 'Administrador', 'admin@algamoney.com', '$2a$10$q6f/kIuEbYddUPOWoqaQ/..gzyEcHHovR0DEuURJ7KgJY6LusTdny');
insert into usuario (codigo, nome, email, senha) values (2, 'Maria Silva', 'maria@algamoney.com', '$2a$10$GtmHx3.gSaZ6bqRjRjhEmeVvjgnUatbG1bXI211750q46RJPHKyiW');

insert into permissao (codigo, descricao) values (1, 'ROLE_CADASTRO_CATEGORIA');
insert into permissao (codigo, descricao) values (2, 'ROLE_PESQUISAR_CATEGORIA');

insert into permissao (codigo, descricao) values (3, 'ROLE_CADASTRO_PESSOA');
insert into permissao (codigo, descricao) values (4, 'ROLE_REMOVER_PESSOA');
insert into permissao (codigo, descricao) values (5, 'ROLE_PESQUISAR_PESSOA');

insert into permissao (codigo, descricao) values (6, 'ROLE_CADASTRO_LANCAMENTO');
insert into permissao (codigo, descricao) values (7, 'ROLE_REMOVER_LANCAMENTO');
insert into permissao (codigo, descricao) values (8, 'ROLE_PESQUISAR_LANCAMENTO');

insert into usuario_permissao (codigo_usuario, codigo_permissao) values (1, 1);
insert into usuario_permissao (codigo_usuario, codigo_permissao) values (1, 2);
insert into usuario_permissao (codigo_usuario, codigo_permissao) values (1, 3);
insert into usuario_permissao (codigo_usuario, codigo_permissao) values (1, 4);
insert into usuario_permissao (codigo_usuario, codigo_permissao) values (1, 5);
insert into usuario_permissao (codigo_usuario, codigo_permissao) values (1, 6);
insert into usuario_permissao (codigo_usuario, codigo_permissao) values (1, 7);
insert into usuario_permissao (codigo_usuario, codigo_permissao) values (1, 8);

insert into usuario_permissao (codigo_usuario, codigo_permissao) values (2, 2);
insert into usuario_permissao (codigo_usuario, codigo_permissao) values (2, 5);
insert into usuario_permissao (codigo_usuario, codigo_permissao) values (2, 8);