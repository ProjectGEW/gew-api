CREATE TABLE secoes(
    id BIGINT NOT NULL AUTO_INCREMENT,
    responsavel_id BIGINT NOT NULL,
    nome VARCHAR(255),

    PRIMARY KEY(id)
);

DROP TRIGGER IF EXISTS `gew_v2`.`secoes_BEFORE_INSERT`;

DELIMITER $$
USE `gew_v2`$$
CREATE DEFINER = CURRENT_USER TRIGGER `gew_v2`.`secoes_BEFORE_INSERT` BEFORE INSERT ON `secoes` FOR EACH ROW
BEGIN
	SET @max := (SELECT MAX(id) FROM secoes);
    SET @count := (SELECT COUNT(id) FROM secoes);

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