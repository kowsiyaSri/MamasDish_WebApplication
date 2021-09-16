package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.beans.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
	
	public List<Country> findByOrderByName();
	
	public Country findByName(String country);

}
