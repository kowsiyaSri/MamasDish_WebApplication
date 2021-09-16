package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.beans.Cuisine;

public interface CuisineRepository extends JpaRepository<Cuisine, Long> {
	
	public List<Cuisine> findByOrderByCuisineName();
	public Cuisine findByCuisineName(String cuisine);
}
