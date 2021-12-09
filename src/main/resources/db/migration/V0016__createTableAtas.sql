CREATE TABLE atas (
      id VARCHAR(255) NOT NULL,
      nome VARCHAR(255) NOT NULL,
      tipo VARCHAR(255) NOT NULL,
      data MEDIUMBLOB NOT NULL,
      criado_em timestamp DEFAULT current_timestamp(),
      projeto_id BIGINT NOT NULL,

      PRIMARY KEY(id)
);

DROP TRIGGER IF EXISTS `gew`.`atas_BEFORE_INSERT`;

DELIMITER $$
USE `gew`$$
CREATE DEFINER = CURRENT_USER TRIGGER `gew`.`atas_BEFORE_INSERT` BEFORE INSERT ON `atas` FOR EACH ROW
BEGIN
	SET @max := (SELECT MAX(id) FROM atas);
    SET @count := (SELECT COUNT(id) FROM atas);

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