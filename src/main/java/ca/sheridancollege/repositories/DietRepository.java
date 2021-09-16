package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.beans.Diet;

public interface DietRepository extends JpaRepository<Diet, Long> {
	
	public Diet findByDietType(String diet);
}
