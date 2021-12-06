package ca.sheridancollege.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.sheridancollege.beans.Rating;
import ca.sheridancollege.beans.Recipe;



public interface RatingRepository extends JpaRepository<Rating, Long> {
	
	// returns all rating for specific recipe 
	@Query(value = "SELECT * FROM rating where recipe_id= ?1 ", nativeQuery = true)
	public List<Rating> findByRecipeId(Long id);

}
