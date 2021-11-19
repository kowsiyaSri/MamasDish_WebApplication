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

	public List<Recipe> findByTitleContainingIgnoreCaseOrCountry_nameContainingIgnoreCase(String search,
			String search2);

	public List<Recipe> findByTitleContainingIgnoreCase(String search);

	public List<Recipe> findByCountry_nameContainingIgnoreCase(String search2);
	
	public List<Recipe> findTop5ByOrderByIdDesc();
		
	public List<Recipe> findByAuthFalse();
	
	public List<Recipe> findByAuthFalseAndCompleteTrue();

	public List<Recipe> findByAuthTrue();

	public List<Recipe> findByIngredients_Ingredient_IngredientNameContainingIgnoreCase(String search2);

	public List<Recipe> findByDiet_Id(Long id);

	public List<Recipe> findByMealtype_id(Long id);

	@Query
	(value = "CALL SuggestRecipe(:user_id)" , nativeQuery = true)
	public List<Recipe> suggestRecipes(@Param("user_id") long user_id);
	
	@Query
	(value = "CALL get_filters(:countries, :diets, :proteins, :cal1, :cal2)" , nativeQuery = true)
	public List<Recipe> getFilterRecipes(@Param("countries") String countries, @Param("diets") String diets,
			@Param("proteins") String proteins, @Param("cal1") float cal1,  @Param("cal2") float cal2);
	
	@Query
	(value = "CALL BasicSearch(:searchInput)" , nativeQuery = true)
	public List<Recipe> basicSearch(@Param("searchInput") String searchInput);
	
	@Query
	(value = "CALL SuggestDiet(:user_id)" , nativeQuery = true)
	public List<Recipe> suggestDiet(@Param("user_id") long user_id);
	
	@Query
	(value = "CALL SuggestCuisine(:user_id)" , nativeQuery = true)
	public List<Recipe> suggestCuisine(@Param("user_id") long user_id);
	
	@Query
	(value = "CALL SuggestCountry(:user_id)" , nativeQuery = true)
	public List<Recipe> suggestCountry(@Param("user_id") long user_id);
	
	@Query
	(value = "CALL SuggestProtein(:user_id)" , nativeQuery = true)
	public List<Recipe> suggestProtein(@Param("user_id") long user_id);
	
	@Transactional
	@Procedure (procedureName = "deleteRecipe")
	public void deleteRecipe(@Param("recipeId") Long recipeId);

}
