package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.beans.RecipesAuthenticated;

public interface RecipesAuthenticatedRepository extends JpaRepository<RecipesAuthenticated, Long> {

	public List<RecipesAuthenticated> findByRecipeIdAndAuthUserId(Long recipeId, Long authUserId);
	
}
