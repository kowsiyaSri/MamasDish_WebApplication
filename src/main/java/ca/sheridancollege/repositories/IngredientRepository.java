package ca.sheridancollege.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.beans.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
	
	//returns ingredient object for a specific name 
	public Ingredient findByIngredientName(String name);
	
}
