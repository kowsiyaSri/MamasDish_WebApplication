DELIMITER $$
CREATE DEFINER=`admin`@`%` PROCEDURE `count_emails`(user_id int)
BEGIN

SELECT COUNT(*) FROM
message_system WHERE is_new = true AND id IN (SELECT messages_id FROM end_user_messages
WHERE end_user_id = user_id);

END$$
DELIMITER ;
