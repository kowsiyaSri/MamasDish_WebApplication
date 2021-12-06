package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.beans.RecipesAuthenticated;

public interface RecipesAuthenticatedRepository extends JpaRepository<RecipesAuthenticated, Long> {

	// returns list of RecipesAuthenticated objects for a specific recipe  and  authenticated user id 
	public List<RecipesAuthenticated> findByRecipeIdAndAuthUserId(Long recipeId, Long authUserId);
	
	// returns list of RecipesAuthenticated objects for a specific recipe
	public List<RecipesAuthenticated> findByRecipeId(Long recipeId);
	
}
