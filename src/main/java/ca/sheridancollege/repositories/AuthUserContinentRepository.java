package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.sheridancollege.beans.AuthUserContinent;
import ca.sheridancollege.beans.EndUser;
import ca.sheridancollege.beans.Recipe;

public interface AuthUserContinentRepository extends JpaRepository<AuthUserContinent, Long> {
	
	public List<AuthUserContinent> findByAuthUserId(Long id);
	
	@Query
	(value = "CALL getAuthUser(:contId)" , nativeQuery = true)
	public List<Integer> getAuthUser(@Param("contId") int contId);
	
}
