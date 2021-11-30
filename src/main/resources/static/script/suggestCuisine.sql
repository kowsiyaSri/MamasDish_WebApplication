DELIMITER $$
CREATE DEFINER=`admin`@`%` PROCEDURE `suggestCuisine`(user_id int)
BEGIN

CREATE TEMPORARY TABLE user_cuisines
SELECT COUNT(*) as 'cuisines', cuisine_id FROM (SELECT cuisine_id FROM recipe WHERE id IN (SELECT recipe_id FROM recent 
WHERE id IN (select recent_id FROM end_user_recent WHERE end_user_id = user_id)
UNION SELECT recipe_id FROM end_user_recipe WHERE end_user_id = user_id)
UNION ALL SELECT cuisine_id FROM end_user_cuisine  WHERE end_user_id = user_id) result
	WHERE cuisine_id IS NOT NULL
	GROUP BY cuisine_id
    ORDER BY cuisines DESC
    LIMIT 3;

SELECT * FROM recipe WHERE cuisine_id IN (SELECT cuisine_id FROM user_cuisines);

DROP TABLE user_cuisines;

END$$
DELIMITER ;
