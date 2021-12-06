package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.sheridancollege.beans.Country;
import ca.sheridancollege.beans.Recipe;

public interface CountryRepository extends JpaRepository<Country, Long> {

	//returns list of country objects order by name
	public List<Country> findByOrderByName();
	
    //returns country object that matches the string parameter
	public Country findByName(String country);

	//returns a list of the country where the first values is the country with the name that equals to the passed parameter 
	@Query(value = "select * from country ORDER BY CASE WHEN name = ?1 THEN 0 ELSE 1 END", nativeQuery = true)
	public List<Country> findByContryName(String countryName);
	
    //returns the first five countries 
	public List<Country> findTop5ByOrderById();

	//calls return_search_countries() procedure that returns countries names that matches countries names for authenticated recipe   
	@Query
	(value = "CALL return_search_countries()" , nativeQuery = true)
	public List<String> getCountryNames();
}
