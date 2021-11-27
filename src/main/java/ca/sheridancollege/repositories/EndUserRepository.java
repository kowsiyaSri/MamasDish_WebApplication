 package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.EndUser;

public interface EndUserRepository extends CrudRepository<EndUser, Long> {

	public EndUser findByEmail(String name);
	
	public List<EndUser> findByRecent_Recipe_Id(Long id);

}
