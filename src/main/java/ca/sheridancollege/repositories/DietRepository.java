package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.sheridancollege.beans.Diet;

public interface DietRepository extends JpaRepository<Diet, Long> {
	
	//returns diet object that its name matches the  string parameter
	public Diet findByDietType(String diet);
	
	//returns a list of the diet objects where the first values is the diet object with the name that equals to the passed parameter  
	@Query(value = "select * from diet ORDER BY CASE WHEN diet_type = ?1 THEN 0 ELSE 1 END", nativeQuery = true)
	public List<Diet> findByDietName(String dietName);
	
	//returns list of strings that represent diet names for authenticated recipes
	@Query
	(value = "CALL get_diet_name()" , nativeQuery = true)
	public List<String> getDietNames();
}
