DELIMITER $$
CREATE DEFINER=`admin`@`%` PROCEDURE `suggestRecipe`(user_id int)
BEGIN
CREATE TEMPORARY TABLE user_countries
SELECT COUNT(*) as 'countries', country_id FROM (SELECT country_id FROM recipe WHERE id IN (SELECT recipe_id FROM recent 
WHERE id IN (select recent_id FROM end_user_recent WHERE end_user_id = user_id)
UNION SELECT recipe_id FROM end_user_recipe WHERE end_user_id = user_id)
UNION ALL SELECT country_id FROM end_user_country  WHERE end_user_id = user_id) result
	GROUP BY country_id
    ORDER BY countries DESC
    LIMIT 3;

CREATE TEMPORARY TABLE user_cuisines
SELECT COUNT(*) as 'cuisines', cuisine_id FROM (SELECT cuisine_id FROM recipe WHERE id IN (SELECT recipe_id FROM recent 
WHERE id IN (select recent_id FROM end_user_recent WHERE end_user_id = user_id)
UNION SELECT recipe_id FROM end_user_recipe WHERE end_user_id = user_id)
UNION ALL SELECT cuisine_id FROM end_user_cuisine  WHERE end_user_id = user_id) result
	WHERE cuisine_id IS NOT NULL
	GROUP BY cuisine_id
    ORDER BY cuisines DESC
    LIMIT 3;
    
CREATE TEMPORARY TABLE user_diets
SELECT COUNT(*) as 'diets', diet_id FROM (SELECT diet_id FROM recipe WHERE id IN (SELECT recipe_id FROM recent 
WHERE id IN (select recent_id FROM end_user_recent WHERE end_user_id = user_id)
UNION SELECT recipe_id FROM end_user_recipe WHERE end_user_id = user_id)
UNION ALL SELECT diet_id FROM end_user_diet  WHERE end_user_id = user_id) result
	WHERE diet_id IS NOT NULL
	GROUP BY diet_id
    ORDER BY diets DESC
    LIMIT 3;

CREATE TEMPORARY TABLE user_proteins
SELECT COUNT(*) as 'proteins', protein_id FROM (SELECT protein_id FROM recipe 
		INNER JOIN recipe_ingredient
		ON recipe.id = recipe_ingredient.recipe_id
		INNER JOIN ingredient
		ON ingredient.id = recipe_ingredient.ingredient_id
        WHERE recipe.id IN (SELECT recipe_id FROM recent 
	WHERE id IN (select recent_id FROM end_user_recent WHERE end_user_id = user_id)
	UNION SELECT recipe_id FROM end_user_recipe WHERE end_user_id = user_id)
	UNION ALL SELECT diet_id FROM end_user_diet  WHERE end_user_id = user_id) result
	WHERE protein_id IS NOT NULL
	GROUP BY protein_id
    ORDER BY proteins DESC
    LIMIT 3;
        
SELECT * FROM recipe where id IN (SELECT id FROM recipe WHERE country_id IN (SELECT country_id FROM user_countries)
	UNION SELECT id FROM recipe WHERE cuisine_id IN (SELECT cuisine_id FROM user_cuisines)
    UNION SELECT id FROM recipe WHERE diet_id IN (SELECT diet_id FROM user_diets)
    UNION SELECT recipe.id FROM recipe 
		INNER JOIN recipe_ingredient
		ON recipe.id = recipe_ingredient.recipe_id
		INNER JOIN ingredient
		ON ingredient.id = recipe_ingredient.ingredient_id
    WHERE protein_id IN (SELECT protein_id FROM user_proteins)
	order by id);

DROP TABLE user_countries;
DROP TABLE user_cuisines;
DROP TABLE user_diets;
DROP TABLE user_proteins;
END$$
DELIMITER ;
