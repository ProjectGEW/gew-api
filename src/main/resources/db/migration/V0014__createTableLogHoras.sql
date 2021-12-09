CREATE TABLE log_horas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    horas INT NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    data DATE NOT NULL,
    criado_em TIMESTAMP DEFAULT now(),

    PRIMARY KEY(id)
);

DROP TRIGGER IF EXISTS `gew`.`log_horas_BEFORE_INSERT`;

DELIMITER $$
USE `gew`$$
CREATE DEFINER = CURRENT_USER TRIGGER `gew`.`log_horas_BEFORE_INSERT` BEFORE INSERT ON `log_horas` FOR EACH ROW
BEGIN
	SET @max := (SELECT MAX(id) FROM log_horas);
    SET @count := (SELECT COUNT(id) FROM log_horas);

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