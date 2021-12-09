CREATE TABLE funcionarios_secoes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    funcionario_cracha BIGINT(20) NOT NULL,
    secao_id BIGINT(20) NOT NULL,

    PRIMARY KEY(id)
);

DROP TRIGGER IF EXISTS `gew`.`funcionarios_secoes_BEFORE_INSERT`;

DELIMITER $$
USE `gew`$$
CREATE DEFINER = CURRENT_USER TRIGGER `gew`.`funcionarios_secoes_BEFORE_INSERT` BEFORE INSERT ON `funcionarios_secoes` FOR EACH ROW
BEGIN
	SET @max := (SELECT MAX(id) FROM funcionarios_secoes);
    SET @count := (SELECT COUNT(id) FROM funcionarios_secoes);

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
