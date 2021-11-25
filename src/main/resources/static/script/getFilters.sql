DELIMITER $$
CREATE DEFINER=`admin`@`%` PROCEDURE `get_filters`(IN countries longtext, IN diets longtext, IN proteins longtext, IN cal1 float, IN cal2 float)
BEGIN

	DROP TABLE IF EXISTS recipe_tbl_1;
    DROP TABLE IF EXISTS recipe_tbl_2;
    DROP TABLE IF EXISTS recipe_tbl_3;
    DROP TABLE IF EXISTS recipe_tbl_4;
	DROP TABLE IF EXISTS recipe_tbl_5;
    
	CREATE TEMPORARY TABLE recipe_tbl_1
	SELECT recipe.id AS recipeId, calories, country.name AS countryName, diet.diet_type AS dietName, protein.protein_type AS proteinName
	FROM ((recipe_ingredient
	INNER JOIN ingredient ON recipe_ingredient.ingredient_id = ingredient.id
	LEFT JOIN protein ON ingredient.protein_id = protein.id)
	INNER JOIN measurement ON recipe_ingredient.measurement_id = measurement.id
	INNER JOIN recipe ON recipe.id = recipe_ingredient.recipe_id
	INNER JOIN country ON recipe.country_id =  country.id
	LEFT JOIN diet ON recipe.diet_id = diet.id ) WHERE recipe.auth = 1;
    
    IF countries != "" THEN SET @sql = CONCAT(
		'CREATE TEMPORARY TABLE recipe_tbl_2
        SELECT * FROM recipe_tbl_1 WHERE countryName IN (',countries,')');
		PREPARE stmt FROM @sql;
		EXECUTE stmt;
		DEALLOCATE PREPARE stmt;
	ELSE 
		CREATE TEMPORARY TABLE recipe_tbl_2
        SELECT * FROM recipe_tbl_1;
	END IF;
    
	IF diets != "" THEN SET @sql = CONCAT(
		'CREATE TEMPORARY TABLE recipe_tbl_3
        SELECT * FROM recipe_tbl_2 WHERE dietName IN (',diets,')');
		PREPARE stmt FROM @sql;
		EXECUTE stmt;
		DEALLOCATE PREPARE stmt;
	ELSE 
		CREATE TEMPORARY TABLE recipe_tbl_3
        SELECT * FROM recipe_tbl_2;
	END IF;
    
	IF proteins != "" THEN SET @sql = CONCAT(
		'CREATE TEMPORARY TABLE recipe_tbl_4
        SELECT * FROM recipe_tbl_3 WHERE proteinName IN (',proteins,')');
		PREPARE stmt FROM @sql;
		EXECUTE stmt;
		DEALLOCATE PREPARE stmt;
	ELSE 
		CREATE TEMPORARY TABLE recipe_tbl_4
        SELECT * FROM recipe_tbl_3;
	END IF;
    
	IF cal2 != 0 THEN SET @sql = CONCAT(
		'CREATE TEMPORARY TABLE recipe_tbl_5
        SELECT * FROM recipe_tbl_4 WHERE calories BETWEEN ',cal1,' AND ',cal2);
		PREPARE stmt FROM @sql;
		EXECUTE stmt;
		DEALLOCATE PREPARE stmt;
	ELSE 
		CREATE TEMPORARY TABLE recipe_tbl_5
        SELECT * FROM recipe_tbl_4;
	END IF;
    
    SELECT * FROM recipe WHERE id IN (SELECT DISTINCT(recipeId) FROM recipe_tbl_5);
    
    DROP TABLE recipe_tbl_1;
    DROP TABLE recipe_tbl_2;
	DROP TABLE recipe_tbl_3;
    DROP TABLE recipe_tbl_4;
	DROP TABLE recipe_tbl_5;
END$$
DELIMITER ;
