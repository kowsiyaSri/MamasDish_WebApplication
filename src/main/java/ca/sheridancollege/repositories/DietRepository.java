package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.sheridancollege.beans.Diet;

public interface DietRepository extends JpaRepository<Diet, Long> {

	@Query(value = "select * from diet ORDER BY CASE WHEN diet_type = ?1 THEN 0 ELSE 1 END", nativeQuery = true)
	public List<Diet> findByDietName(String dietName);
}
