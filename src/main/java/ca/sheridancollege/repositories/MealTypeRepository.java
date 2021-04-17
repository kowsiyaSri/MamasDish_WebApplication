package ca.sheridancollege.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.MealType;

@Repository 
public interface MealTypeRepository extends JpaRepository<MealType, Long> {
	
	
}
