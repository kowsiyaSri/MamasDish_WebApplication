package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.beans.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	
	public List<Recipe> findByTitleContainingIgnoreCaseOrCountry_nameContainingIgnoreCase(String search, String search2);
	
	public List<Recipe> findByTitleContainingIgnoreCase(String search);
	
	public List<Recipe> findByCountry_nameContainingIgnoreCase(String search2);
	
	public List<Recipe> findByIngredients_Ingredient_IngredientNameContainingIgnoreCase(String search2);



}
