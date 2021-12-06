 package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.EndUser;

public interface EndUserRepository extends CrudRepository<EndUser, Long> {

	// returns the EndUser object that matches the string parameter
	public EndUser findByEmail(String name);
	
	//returns list of enduser objects that matches a specific recent recipe id 
	public List<EndUser> findByRecent_Recipe_Id(Long id);

}
