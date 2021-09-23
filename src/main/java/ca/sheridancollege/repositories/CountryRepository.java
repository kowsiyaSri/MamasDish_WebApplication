package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.beans.Country;
import ca.sheridancollege.beans.Recipe;

public interface CountryRepository extends JpaRepository<Country, Long> {
	
	public List<Country> findByOrderByName();
	
	public List<Country> findTop5ByOrderById();

}
