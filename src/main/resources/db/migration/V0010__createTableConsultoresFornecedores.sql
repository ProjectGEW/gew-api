CREATE TABLE consultores_fornecedores(
    id BIGINT NOT NULL AUTO_INCREMENT,
    funcionario_cracha BIGINT NOT NULL,
    fornecedor_id BIGINT NOT NULL,

    PRIMARY KEY(id)
);