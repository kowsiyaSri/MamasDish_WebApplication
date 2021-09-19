package ca.sheridancollege.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.beans.UserPreference;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {
	
	public UserPreference findByEnduser_id(long id);
	
}
