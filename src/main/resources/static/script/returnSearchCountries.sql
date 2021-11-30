DELIMITER $$
CREATE DEFINER=`admin`@`%` PROCEDURE `return_search_countries`()
BEGIN
	SELECT name FROM country WHERE id IN (SELECT country_id FROM recipe);
END$$
DELIMITER ;
