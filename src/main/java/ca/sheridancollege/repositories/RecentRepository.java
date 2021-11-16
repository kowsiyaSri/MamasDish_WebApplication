package ca.sheridancollege.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.beans.Recent;

public interface RecentRepository extends JpaRepository<Recent, Long> {
	
	

}
