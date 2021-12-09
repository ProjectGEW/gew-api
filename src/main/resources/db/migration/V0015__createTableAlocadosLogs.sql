CREATE TABLE alocados_logs(
    id BIGINT NOT NULL AUTO_INCREMENT,
    alocado_id BIGINT NOT NULL,
    log_id BIGINT NOT NULL,

    PRIMARY KEY(id)
);

DROP TRIGGER IF EXISTS `gew`.`alocados_logs_BEFORE_INSERT`;

DELIMITER $$
USE `gew`$$
CREATE DEFINER = CURRENT_USER TRIGGER `gew`.`alocados_logs_BEFORE_INSERT` BEFORE INSERT ON `alocados_logs` FOR EACH ROW
BEGIN
	SET @max := (SELECT MAX(id) FROM alocados_logs);
    SET @count := (SELECT COUNT(id) FROM alocados_logs);

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