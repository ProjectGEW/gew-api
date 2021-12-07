CREATE TABLE log_horas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    horas INT NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    data DATE NOT NULL,
    criado_em TIMESTAMP DEFAULT now(),

    PRIMARY KEY(id)
);