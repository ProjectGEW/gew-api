CREATE TABLE consultores_skills (
    id BIGINT NOT NULL AUTO_INCREMENT,
    funcionario_cracha BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,

    PRIMARY KEY(id)
);

DROP TRIGGER IF EXISTS `gew`.`consultores_skills_BEFORE_INSERT`;

DELIMITER $$
USE `gew`$$
CREATE DEFINER = CURRENT_USER TRIGGER `gew`.`consultores_skills_BEFORE_INSERT` BEFORE INSERT ON `consultores_skills` FOR EACH ROW
BEGIN
	SET @max := (SELECT MAX(id) FROM consultores_skills);
    SET @count := (SELECT COUNT(id) FROM consultores_skills);

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