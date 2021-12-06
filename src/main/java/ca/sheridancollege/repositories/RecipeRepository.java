package ca.sheridancollege.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import ca.sheridancollege.beans.Country;
import ca.sheridancollege.beans.MessageSystem;
import ca.sheridancollege.beans.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

	//returns list of recipes for specific title or country name
	public List<Recipe> findByTitleContainingIgnoreCaseOrCountry_nameContainingIgnoreCase(String search,
			String search2);

	//returns list recipes of specific title 
	public List<Recipe> findByTitleContainingIgnoreCase(String search);

	//returns list recipes of specific country 
	public List<Recipe> findByCountry_nameContainingIgnoreCase(String search2);
	
	//returns list of first five recipes
	public List<Recipe> findTop5ByOrderByIdDesc();
	
	//returns list of not authenticated  recipes 
	public List<Recipe> findByAuthFalse();
	
	//returns list of authenticated and approved recipes  
	public List<Recipe> findByAuthFalseAndCompleteTrue();

	//returns list of authenticated recipes   
	public List<Recipe> findByAuthTrue();

	//returns list of recipes for specific ingredient
	public List<Recipe> findByIngredients_Ingredient_IngredientNameContainingIgnoreCase(String search2);

	//returns list of recipes for specific diet
	public List<Recipe> findByDiet_Id(Long id);

	//returns list of recipes for specific meal type
	public List<Recipe> findByMealtype_id(Long id);

	//returns list of recipes by calling SuggestRecipe procedure
	@Query
	(value = "CALL SuggestRecipe(:user_id)" , nativeQuery = true)
	public List<Recipe> suggestRecipes(@Param("user_id") long user_id);
	
	//returns list of recipes by calling get filters procedure
	@Query
	(value = "CALL get_filters(:countries, :diets, :proteins, :cal1, :cal2)" , nativeQuery = true)
	public List<Recipe> getFilterRecipes(@Param("countries") String countries, @Param("diets") String diets,
			@Param("proteins") String proteins, @Param("cal1") float cal1,  @Param("cal2") float cal2);
	
	//returns list of recipes by calling basciSearch procedure 
	@Query
	(value = "CALL BasicSearch(:searchInput)" , nativeQuery = true)
	public List<Recipe> basicSearch(@Param("searchInput") String searchInput);
	
	//returns list of recipes by calling SuggestDiet procedure for specific user
	@Query
	(value = "CALL SuggestDiet(:user_id)" , nativeQuery = true)
	public List<Recipe> suggestDiet(@Param("user_id") long user_id);
	
	//returns list of recipes by calling SuggestCuisine procedure for specific user
	@Query
	(value = "CALL SuggestCuisine(:user_id)" , nativeQuery = true)
	public List<Recipe> suggestCuisine(@Param("user_id") long user_id);
	
	//returns list of recipes by calling suggestCountry procedure for specific user
	@Query
	(value = "CALL SuggestCountry(:user_id)" , nativeQuery = true)
	public List<Recipe> suggestCountry(@Param("user_id") long user_id);
	
	//returns list of recipes by calling SuggestProtein procedure for specific user
	@Query
	(value = "CALL SuggestProtein(:user_id)" , nativeQuery = true)
	public List<Recipe> suggestProtein(@Param("user_id") long user_id);
	
	//delets a recipes by calling deleteRecipeUpdate procedure
	@Transactional
	@Procedure (procedureName = "deleteRecipeUpdate")
	public void deleteRecipe(@Param("recipeId") Long recipeId);

}
