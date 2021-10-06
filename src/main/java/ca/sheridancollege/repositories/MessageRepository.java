package ca.sheridancollege.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.sheridancollege.beans.MessageSystem;

public interface MessageRepository extends JpaRepository<MessageSystem, Long> {
	

	@Query
	(value = "CALL count_emails(:user_id)" , nativeQuery = true)
	public int emailCount(@Param("user_id") Long user_id);
	
}
