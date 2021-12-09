CREATE TABLE alocados(
    id BIGINT NOT NULL AUTO_INCREMENT,
    projeto_id BIGINT NOT NULL,
    funcionario_cracha BIGINT NOT NULL,
    horas_totais INT NOT NULL,
    status BOOLEAN NOT NULL,

    PRIMARY KEY(id)
);

DROP TRIGGER IF EXISTS `gew`.`alocados_BEFORE_INSERT`;

DELIMITER $$
USE `gew`$$
CREATE DEFINER = CURRENT_USER TRIGGER `gew`.`alocados_BEFORE_INSERT` BEFORE INSERT ON `alocados` FOR EACH ROW
BEGIN
	SET @max := (SELECT MAX(id) FROM alocados);
    SET @count := (SELECT COUNT(id) FROM alocados);

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