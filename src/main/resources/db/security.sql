CREATE TABLE perfil
(
    id BIGINT NOT NULL PRIMARY KEY IDENTITY(1,1),
    descricao varchar(50) not null unique
);

CREATE TABLE usuario
(
    id BIGINT NOT NULL PRIMARY KEY IDENTITY(1,1),
    ativo bit not null,
    email varchar(255) not null unique,
    senha varchar(255) not null,
    codigo_verificador varchar(6) DEFAULT NULL,
);

CREATE TABLE usuario_perfil(
    usuario_id bigint not null, FOREIGN KEY(usuario_id) REFERENCES usuario(id),
    perfil_id bigint not null, FOREIGN KEY(perfil_id) REFERENCES perfil(id),
    CONSTRAINT tb_usuario_perfil_pk 
      PRIMARY KEY (usuario_id, perfil_id)
);

-- insert admin user:bilas@agiotagem.com pass: admin
insert into usuario
(ativo, email, senha)
VALUES
(1, 'bilas@agiotagem.com', '$2a$10$DgT21u9/0/4FrnyKU/zGYOBk8C3uyYF1EiVG5AGv5Wgcbzy4ICIp2')

insert into perfil values 
('ADMIN'), ('USUARIO')

insert into usuario_perfil values
(1,1)

-- insert common user:usuario@agiotagem.com pass: user
insert into usuario
(ativo, email, senha)
VALUES
(1, 'usuario@agiotagem.com', '$2a$10$E45PJbrRV6Rr40WhLHHGGO9lO7bDXLG71QnYe.0qF.VlJ/szS6QaC')

insert into usuario_perfil values
((select id from perfil where descricao = 'USUARIO'),
(select id from usuario where email = 'usuario@agiotagem.com'))