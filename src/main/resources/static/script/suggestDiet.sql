DELIMITER $$
CREATE DEFINER=`admin`@`%` PROCEDURE `suggestDiet`(user_id int)
BEGIN

CREATE TEMPORARY TABLE user_diets
SELECT COUNT(*) as 'diets', diet_id FROM (SELECT diet_id FROM recipe WHERE id IN (SELECT recipe_id FROM recent 
WHERE id IN (select recent_id FROM end_user_recent WHERE end_user_id = user_id)
UNION SELECT recipe_id FROM end_user_recipe WHERE end_user_id = user_id)
UNION ALL SELECT diet_id FROM end_user_diet  WHERE end_user_id = user_id) result
	WHERE diet_id IS NOT NULL
	GROUP BY diet_id
    ORDER BY diets DESC
    LIMIT 3;

SELECT * FROM recipe WHERE diet_id IN (SELECT diet_id FROM user_diets);
DROP TABLE user_diets;
END$$
DELIMITER ;
