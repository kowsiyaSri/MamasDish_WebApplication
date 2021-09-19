package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.sheridancollege.beans.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

	public List<Country> findByOrderByName();

	@Query(value = "select * from country ORDER BY CASE WHEN name = ?1 THEN 0 ELSE 1 END", nativeQuery = true)
	public List<Country> findByContryName(String countryName);

}
