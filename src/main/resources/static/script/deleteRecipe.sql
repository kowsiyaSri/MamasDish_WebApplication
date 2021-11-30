DELIMITER $$
CREATE DEFINER=`admin`@`%` PROCEDURE `deleteRecipe`(recipeId long)
BEGIN

 DELETE FROM recipe_instructions where recipe_id = recipeId;
 
 DELETE FROM instruction where id IN
 (SELECT instructions_id from recipe_instructions where recipe_id = recipeId  );

 DELETE FROM recipe_ingredient where recipe_id = recipeId;
 
 DELETE FROM recipe where id = recipeId;
END$$
DELIMITER ;
