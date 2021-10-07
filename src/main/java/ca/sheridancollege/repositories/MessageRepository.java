package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.sheridancollege.beans.MessageSystem;

public interface MessageRepository extends JpaRepository<MessageSystem, Long> {
	

	@Query
	(value = "CALL count_emails(:user_id)" , nativeQuery = true)
	public int emailCount(@Param("user_id") Long user_id);
	
	@Query
	(value = "CALL count_deleted_emails(:user_id)" , nativeQuery = true)
	public int countDeletedEmails(@Param("user_id") Long user_id);
	
	public List<MessageSystem> findByIsDeletedTrue();
	
	@Query
	(value = "CALL get_deleted_emails(:user_id)" , nativeQuery = true)
	public List<MessageSystem> getDeletedEmails(@Param("user_id") Long user_id);
	
	@Query
	(value = "CALL get_emails(:user_id)" , nativeQuery = true)
	public List<MessageSystem> getEmails(@Param("user_id") Long user_id);
	
}
