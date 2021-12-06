package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.beans.Recent;

public interface RecentRepository extends JpaRepository<Recent, Long> {
	
	//returns list of recent objects for a specific recipe
	public List<Recent> findByRecipeId(Long id);

}
