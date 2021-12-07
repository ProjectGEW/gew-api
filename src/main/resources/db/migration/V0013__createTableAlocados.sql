CREATE TABLE alocados(
    id BIGINT NOT NULL AUTO_INCREMENT,
    projeto_id BIGINT NOT NULL,
    funcionario_cracha BIGINT NOT NULL,
    horas_totais INT NOT NULL,
    status BOOLEAN NOT NULL,

    PRIMARY KEY(id)
);