package ca.sheridancollege.repositories;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.EndUser;

public interface EndUserRepository extends CrudRepository<EndUser, Long> {

	public EndUser findByEmail(String name);

}
