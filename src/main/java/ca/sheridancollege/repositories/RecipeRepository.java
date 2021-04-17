package ca.sheridancollege.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.beans.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
