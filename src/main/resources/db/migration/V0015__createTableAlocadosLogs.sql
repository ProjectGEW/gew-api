CREATE TABLE alocados_logs(
    id BIGINT NOT NULL AUTO_INCREMENT,
    alocado_id BIGINT NOT NULL,
    log_id BIGINT NOT NULL,

    PRIMARY KEY(id)
);