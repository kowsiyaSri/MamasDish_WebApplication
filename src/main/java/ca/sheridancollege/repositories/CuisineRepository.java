package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.beans.Cuisine;

public interface CuisineRepository extends JpaRepository<Cuisine, Long> {
	//returns list of cuisine objects ordered by name 
	public List<Cuisine> findByOrderByCuisineName();
	
	//returns cuisine object that matches the string parameter
	public Cuisine findByCuisineName(String cuisine);
}
