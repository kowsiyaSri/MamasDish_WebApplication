DELIMITER $$
CREATE DEFINER=`admin`@`%` PROCEDURE `suggestCountry`(user_id int)
BEGIN

CREATE TEMPORARY TABLE user_countries
SELECT COUNT(*) as 'countries', country_id FROM (SELECT country_id FROM recipe WHERE id IN (SELECT recipe_id FROM recent 
WHERE id IN (select recent_id FROM end_user_recent WHERE end_user_id = user_id)
UNION SELECT recipe_id FROM end_user_recipe WHERE end_user_id = user_id)
UNION ALL SELECT country_id FROM end_user_country  WHERE end_user_id = user_id) result
	GROUP BY country_id
    ORDER BY countries DESC
    LIMIT 3;
    
SELECT * FROM recipe WHERE country_id IN (SELECT country_id FROM user_countries);

DROP TABLE user_countries;
END$$
DELIMITER ;
