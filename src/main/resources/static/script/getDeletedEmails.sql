DELIMITER $$
CREATE DEFINER=`admin`@`%` PROCEDURE `get_deleted_emails`(user_id int)
BEGIN
SELECT * FROM
message_system WHERE is_deleted = true AND id IN (SELECT messages_id FROM end_user_messages
WHERE end_user_id = user_id)
ORDER BY date_sent;
END$$
DELIMITER ;
