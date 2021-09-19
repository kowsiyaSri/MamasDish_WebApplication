package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.sheridancollege.beans.RecipeIngredient;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

	@Query(value = "select id from recipe_ingredient where recipe_id= ?1", nativeQuery = true)
	List<Long> findByRecipe(Long recipeId);

	@Query(value = "select quantity from recipe_ingredient where recipe_id= ?1", nativeQuery = true)
	List<Float> findQuantity(Long recipeId);

	@Query(value = "select ingredient.ingredient_name\r\n" + "from ingredient\r\n"
			+ "inner join recipe_ingredient   on ingredient.id = recipe_ingredient.ingredient_id where recipe_ingredient.recipe_id=?1", nativeQuery = true)
	List<String> findIngredientName(Long recipeId);

	@Query(value = "select protein_id\r\n" + "from ingredient\r\n"
			+ "inner join recipe_ingredient   on ingredient.id = recipe_ingredient.ingredient_id where recipe_ingredient.recipe_id=?1", nativeQuery = true)
	List<Integer> findProtien(Long recipeId);

	@Query(value = "DELETE FROM recipe_ingredient WHERE recipe_id = ?1", nativeQuery = true)
	public void deleteIngredientsRecords(Long recipeId);
	
	

}
