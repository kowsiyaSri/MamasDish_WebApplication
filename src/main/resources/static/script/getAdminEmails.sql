DELIMITER $$
CREATE DEFINER=`admin`@`%` PROCEDURE `get_admin_emails`()
BEGIN
SELECT COUNT(*) FROM message_system
WHERE receiver LIKE "Mama's Dish Admin" AND is_new = 1
ORDER BY date_sent DESC;
END$$
DELIMITER ;
