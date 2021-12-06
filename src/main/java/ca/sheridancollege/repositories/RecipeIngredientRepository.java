package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ca.sheridancollege.beans.RecipeIngredient;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

	// delete recipe by passing the recipe id 
	public long deleteByRecipeId(long id);
}
