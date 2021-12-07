CREATE TABLE funcionarios(
    numero_cracha BIGINT NOT NULL,
    nome VARCHAR(200) NOT NULL,
    cpf VARCHAR(12) NOT NULL,
    telefone VARCHAR(14) NOT NULL,
    valor_hora DOUBLE NOT NULL,
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,

    PRIMARY KEY (numero_cracha)
);