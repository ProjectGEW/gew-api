CREATE TABLE despesas(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    esforco INT NOT NULL,
    valor DOUBLE NOT NULL,
    projeto_id BIGINT NOT NULL,

    PRIMARY KEY(id)
);

DROP TRIGGER IF EXISTS `gew_v2`.`despesas_BEFORE_INSERT`;

DELIMITER $$
USE `gew`$$
CREATE DEFINER = CURRENT_USER TRIGGER `gew_v2`.`despesas_BEFORE_INSERT` BEFORE INSERT ON `despesas` FOR EACH ROW
BEGIN
	SET @max := (SELECT MAX(id) FROM despesas);
    SET @count := (SELECT COUNT(id) FROM despesas);

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