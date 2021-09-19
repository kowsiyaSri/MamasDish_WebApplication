package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.sheridancollege.beans.Cuisine;

public interface CuisineRepository extends JpaRepository<Cuisine, Long> {

	public List<Cuisine> findByOrderByCuisineName();

	@Query(value = "select * from cuisine ORDER BY CASE WHEN cuisine_name = ?1 THEN 0 ELSE 1 END", nativeQuery = true)
	public List<Cuisine> findByCuisineName(String cuisineName);
}
