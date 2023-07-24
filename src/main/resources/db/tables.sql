DROP TABLE IF EXISTS controle_emprestimo_parcela;
DROP TABLE IF EXISTS emprestimo;
DROP TABLE IF EXISTS endividado;
DROP TABLE IF EXISTS cartao;

CREATE TABLE cartao
(
    id BIGINT NOT NULL PRIMARY KEY IDENTITY(1,1),
    ativo BIT NOT NULL,

    nome VARCHAR(200) NOT NULL,
    numero_final INT NOT NULL,
    limite DECIMAL(19,2) NOT NULL,
    dia_vencimento INT NOT NULL
);

CREATE TABLE endividado
(
    id BIGINT NOT NULL PRIMARY KEY IDENTITY(1,1),
    ativo BIT NOT NULL,

    nome VARCHAR(200) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
);

CREATE TABLE emprestimo
(
    id BIGINT NOT NULL PRIMARY KEY IDENTITY(1,1),
    ativo BIT NOT NULL,

    nome VARCHAR(200) NOT NULL,
    valor_total DECIMAL(19,2) NOT NULL,
    qtd_parcelas INT NOT NULL,
    valor_parcela DECIMAL(19,2) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    cartao_id BIGINT NOT NULL FOREIGN KEY REFERENCES cartao(id), 
    endividado_id BIGINT NOT NULL FOREIGN KEY REFERENCES endividado(id)
);

CREATE TABLE controle_emprestimo_parcela
(
    id BIGINT NOT NULL PRIMARY KEY IDENTITY(1,1),
    ativo BIT NOT NULL,

    emprestimo_id BIGINT NOT NULL FOREIGN KEY REFERENCES emprestimo(id), 
    data_emprestimo DATETIME NOT NULL,
    data_vencimento DATE NOT NULL,
    numero_parcela int NOT NULL,
    parcela_atual BIT NOT NULL,
    status VARCHAR(50)
);

ALTER TABLE controle_emprestimo_parcela ADD desconto DECIMAL(19,2) NULL;
ALTER TABLE cartao add usuario_id bigint not null, FOREIGN KEY(usuario_id) REFERENCES usuario(id); 