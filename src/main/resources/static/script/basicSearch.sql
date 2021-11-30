use MAMASDISH;


CALL `MAMASDISH`.`get_diet_name`();

DELIMITER $$
CREATE DEFINER=`admin`@`%` PROCEDURE `basicSearch`(searchInput varchar(100))
BEGIN
CREATE TEMPORARY TABLE recipe_tbl
SELECT recipe.id AS recipeId, title, ingredient_name, duration, description, country.name AS countryName, cuisine.cuisine_name AS cuisine, protein.protein_type AS protein
FROM ((recipe_ingredient
INNER JOIN ingredient ON recipe_ingredient.ingredient_id = ingredient.id
LEFT JOIN protein ON ingredient.protein_id = protein.id)
INNER JOIN measurement ON recipe_ingredient.measurement_id = measurement.id
INNER JOIN recipe ON recipe.id = recipe_ingredient.recipe_id
INNER JOIN country ON recipe.country_id =  country.id
LEFT JOIN cuisine ON recipe.cuisine_id = cuisine.id );

SELECT * FROM recipe WHERE recipe.id IN (
SELECT distinct(recipeId) FROM recipe_tbl WHERE 
INSTR(LOWER(searchInput), LOWER(countryName)) > 0 OR INSTR(LOWER(searchInput), LOWER(cuisine)) > 0
OR LOWER(title) LIKE CONCAT('%', LOWER(searchInput), '%') OR INSTR(LOWER(searchInput), LOWER(ingredient_name)) > 0
OR INSTR(LOWER(searchInput), duration) > 0 OR INSTR(searchInput, LOWER(protein)) > 0
ORDER BY CASE
WHEN INSTR(LOWER(searchInput), LOWER(countryName)) > 0 THEN 20
WHEN INSTR(LOWER(searchInput), LOWER(cuisine)) > 0 THEN 20
WHEN LOWER(title) LIKE CONCAT('%', LOWER(searchInput), '%')THEN 15
WHEN INSTR(LOWER(searchInput), LOWER(ingredient_name)) > 0 THEN 10
WHEN INSTR(LOWER(searchInput), duration) > 0 THEN 5
ELSE 0
END)
LIMIT 10;

DROP TABLE recipe_tbl;
END$$
DELIMITER ;


SELECT protein_type FROM protein WHERE id IN (SELECT protein_id FROM ingredient 
		INNER JOIN recipe_ingredient ON recipe_ingredient.ingredient_id = ingredient.id
        INNER JOIN recipe on recipe.id = recipe_ingredient.recipe_id
        WHERE recipe.auth = 1);