DELIMITER $$
CREATE DEFINER=`admin`@`%` PROCEDURE `get_deleted_admin_emails`()
BEGIN
SELECT * FROM message_system
WHERE receiver LIKE "Mama's Dish Admin" AND is_deleted = 1
ORDER BY date_sent DESC;
END$$
DELIMITER ;
