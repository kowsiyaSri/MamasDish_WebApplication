package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.MealType;

@Repository 
public interface MealTypeRepository extends JpaRepository<MealType, Long> {
	
	@Query(value = "select * from meal_type ORDER BY CASE WHEN meal_name = ?1 THEN 0 ELSE 1 END", nativeQuery = true)
	public List<MealType> findByMealName(String mealName);
}
