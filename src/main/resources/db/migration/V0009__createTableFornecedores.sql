CREATE TABLE fornecedores (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    cnpj VARCHAR(18) NOT NULL,
    email VARCHAR(255) NOT NULL,
    telefone VARCHAR(14) NOT NULL,

    PRIMARY KEY(id)
);

DROP TRIGGER IF EXISTS `gew`.`fornecedores_BEFORE_INSERT`;

DELIMITER $$
USE `gew`$$
CREATE DEFINER = CURRENT_USER TRIGGER `gew`.`fornecedores_BEFORE_INSERT` BEFORE INSERT ON `fornecedores` FOR EACH ROW
BEGIN
	SET @max := (SELECT MAX(id) FROM fornecedores);
    SET @count := (SELECT COUNT(id) FROM fornecedores);

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