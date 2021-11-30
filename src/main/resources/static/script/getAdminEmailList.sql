DELIMITER $$
CREATE DEFINER=`admin`@`%` PROCEDURE `get_admin_email_list`()
BEGIN
SELECT * FROM message_system
WHERE receiver LIKE "Mama's Dish Admin" AND is_deleted = false
ORDER BY date_sent DESC;
END$$
DELIMITER ;
