package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.sheridancollege.beans.MessageSystem;

public interface MessageRepository extends JpaRepository<MessageSystem, Long> {
	
    //returns the number of emails by calling count_emails procedure for specific user
	@Query
	(value = "CALL count_emails(:user_id)" , nativeQuery = true)
	public int emailCount(@Param("user_id") Long user_id);
	
	//return number of deleted emails by calling count_deleted_emails procedure for specific user
	@Query
	(value = "CALL count_deleted_emails(:user_id)" , nativeQuery = true)
	public int countDeletedEmails(@Param("user_id") Long user_id);
	
	//returns list of deleted message system
	public List<MessageSystem> findByIsDeletedTrue();
	
	//returns list of deleted message systems for specific user 
	@Query
	(value = "CALL get_deleted_emails(:user_id)" , nativeQuery = true)
	public List<MessageSystem> getDeletedEmails(@Param("user_id") Long user_id);
	
	//returns list of system messages for specific user
	@Query
	(value = "CALL get_emails(:user_id)" , nativeQuery = true)
	public List<MessageSystem> getEmails(@Param("user_id") Long user_id);
	
	//returns number of admin's emailes 
	@Query
	(value = "CALL get_admin_emails()" , nativeQuery = true)
	public int getAdminEmailCount();	
	
	//returns list of deleted system messages for mama's dish admin
	@Query
	(value = "CALL get_deleted_admin_emails()" , nativeQuery = true)
	public List<MessageSystem> getAdminDeletedEmails();
	
	//returns list of not deleted system messages for mama's dish admin
	@Query
	(value = "CALL get_admin_email_list()" , nativeQuery = true)
	public List<MessageSystem> getAdminEmailList();

	//returns list of  system messages for specific reciver
	public List<MessageSystem> findByReceiverLike(String string);
}
