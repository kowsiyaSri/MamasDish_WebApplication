package ca.sheridancollege.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.beans.Chef;

public interface ChefRepository extends JpaRepository<Chef, Long> {
	
	//returns chef object for a specific email address
	public Chef findByEnduser_Email(String username);

}
