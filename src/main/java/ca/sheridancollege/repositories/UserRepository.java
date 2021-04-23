package ca.sheridancollege.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	
	public User findByUsername(String username);
	
	
}
