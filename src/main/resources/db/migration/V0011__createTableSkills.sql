CREATE TABLE skills (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,

    PRIMARY KEY(id)
);

DROP TRIGGER IF EXISTS `gew`.`skills_BEFORE_INSERT`;

DELIMITER $$
USE `gew`$$
CREATE DEFINER = CURRENT_USER TRIGGER `gew`.`skills_BEFORE_INSERT` BEFORE INSERT ON `skills` FOR EACH ROW
BEGIN
	SET @max := (SELECT MAX(id) FROM skills);
    SET @count := (SELECT COUNT(id) FROM skills);

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