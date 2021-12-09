CREATE TABLE cargos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,

    PRIMARY KEY(id)
);

DROP TRIGGER IF EXISTS `gew`.`cargos_BEFORE_INSERT`;

DELIMITER $$
USE `gew`$$
CREATE DEFINER = CURRENT_USER TRIGGER `gew`.`cargos_BEFORE_INSERT` BEFORE INSERT ON `cargos` FOR EACH ROW
BEGIN
	SET @max := (SELECT MAX(id) FROM cargos);
    SET @count := (SELECT COUNT(id) FROM cargos);

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