DELIMITER $$
CREATE DEFINER=`admin`@`%` PROCEDURE `basicSearch`(searchInput varchar(100))
BEGIN
CREATE TEMPORARY TABLE recipe_tbl
SELECT recipe.id, title, ingredient_name, duration, description, country.name AS countryName
FROM ((recipe_ingredient
INNER JOIN ingredient ON recipe_ingredient.ingredient_id = ingredient.id)
INNER JOIN measurement ON recipe_ingredient.measurement_id = measurement.id
INNER JOIN recipe ON recipe.id = recipe_ingredient.recipe_id
INNER JOIN country ON recipe.country_id =  country.id);

SELECT * FROM recipe WHERE recipe.id IN (SELECT distinct(id) FROM recipe_tbl WHERE CONCAT(title, '',ingredient_name , '', description, '', countryName, '', duration ) LIKE CONCAT('%',searchInput,'%'));



DROP TABLE recipe_tbl;
END$$
DELIMITER ;
