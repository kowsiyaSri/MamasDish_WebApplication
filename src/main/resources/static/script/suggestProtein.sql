DELIMITER $$
CREATE DEFINER=`admin`@`%` PROCEDURE `suggestProtein`(user_id int)
BEGIN

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
    
SELECT * FROM recipe 
		INNER JOIN recipe_ingredient
		ON recipe.id = recipe_ingredient.recipe_id
		INNER JOIN ingredient
		ON ingredient.id = recipe_ingredient.ingredient_id
        WHERE protein_id IN (SELECT protein_id FROM user_proteins);
        
DROP TABLE user_proteins;
END$$
DELIMITER ;
