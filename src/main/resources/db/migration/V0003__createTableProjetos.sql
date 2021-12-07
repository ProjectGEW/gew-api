CREATE TABLE projetos(
    id BIGINT NOT NULL AUTO_INCREMENT,
    numero_do_projeto INT NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    descricao TEXT,
    ata VARCHAR(80) NOT NULL,
    solicitante_id BIGINT NOT NULL,
    responsavel_id BIGINT NOT NULL,
    data_de_inicio VARCHAR(15) NOT NULL,
    data_de_termino VARCHAR(15) NOT NULL,
    data_de_aprovacao VARCHAR(15) NOT NULL,
    status_projeto VARCHAR(15) NOT NULL DEFAULT 0,
    secao VARCHAR(255),
    horas_apontadas INT NOT NULL,
    data_do_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_da_conclusao DATE,

    PRIMARY KEY(id)
);

DROP TRIGGER IF EXISTS `gew_v2`.`projetos_BEFORE_INSERT`;

DELIMITER $$
USE `gew_v2`$$
CREATE DEFINER = CURRENT_USER TRIGGER `gew_v2`.`projetos_BEFORE_INSERT` BEFORE INSERT ON `projetos` FOR EACH ROW
BEGIN
	SET @max := (SELECT MAX(id) FROM projetos);
    SET @count := (SELECT COUNT(id) FROM projetos);

    if (@max = @count) THEN
		SET @next_value := @max + 1;
    elseif (@count = 0) THEN
        SET @next_value := 1;
    else
		SET @next_value := @count;
    end if;

    SET NEW.id = @next_value;
END$$
DELIMITER ;