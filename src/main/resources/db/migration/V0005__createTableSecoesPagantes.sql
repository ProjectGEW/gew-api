CREATE TABLE secoes_pagantes(
    id BIGINT NOT NULL AUTO_INCREMENT,
    secao_id BIGINT NOT NULL,
    percentual INT NOT NULL,
    valor DOUBLE NOT NULL,
    projeto_id BIGINT NOT NULL,

    PRIMARY KEY(id)
);

DROP TRIGGER IF EXISTS `gew_v2`.`secoes_pagantes_BEFORE_INSERT`;

DELIMITER $$
USE `gew`$$
CREATE DEFINER = CURRENT_USER TRIGGER `gew_v2`.`secoes_pagantes_BEFORE_INSERT` BEFORE INSERT ON `secoes_pagantes` FOR EACH ROW
BEGIN
	SET @max := (SELECT MAX(id) FROM secoes_pagantes);
    SET @count := (SELECT COUNT(id) FROM secoes_pagantes);

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
