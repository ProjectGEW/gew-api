CREATE TABLE atas (
      id VARCHAR(255) NOT NULL,
      nome VARCHAR(255) NOT NULL,
      tipo VARCHAR(255) NOT NULL,
      data MEDIUMBLOB NOT NULL,
      criado_em timestamp DEFAULT current_timestamp(),
      projeto_id BIGINT NOT NULL,

      PRIMARY KEY(id)
);