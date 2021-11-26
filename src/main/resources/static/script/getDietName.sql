DELIMITER $$
CREATE DEFINER=`admin`@`%` PROCEDURE `get_diet_name`()
BEGIN
	SELECT diet_type FROM diet WHERE id IN (SELECT diet_id FROM recipe WHERE auth = 1);
END$$
DELIMITER ;
