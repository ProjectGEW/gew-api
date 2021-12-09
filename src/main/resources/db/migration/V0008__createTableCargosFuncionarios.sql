CREATE TABLE cargos_funcionarios(
    id BIGINT NOT NULL AUTO_INCREMENT,
    funcionario_cracha BIGINT NOT NULL,
    cargo_id BIGINT NOT NULL,

    PRIMARY KEY(id)
);

DROP TRIGGER IF EXISTS `gew`.`cargos_funcionarios_BEFORE_INSERT`;

DELIMITER $$
USE `gew`$$
CREATE DEFINER = CURRENT_USER TRIGGER `gew`.`cargos_funcionarios_BEFORE_INSERT` BEFORE INSERT ON `cargos_funcionarios` FOR EACH ROW
BEGIN
	SET @max := (SELECT MAX(id) FROM cargos_funcionarios);
    SET @count := (SELECT COUNT(id) FROM cargos_funcionarios);

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