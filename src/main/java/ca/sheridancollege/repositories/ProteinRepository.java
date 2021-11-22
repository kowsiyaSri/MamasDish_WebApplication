package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.sheridancollege.beans.Protein;

public interface ProteinRepository extends JpaRepository<Protein, Long> {
	
	public Protein findByProteinType(String protein);
	
	@Query
	(value = "CALL get_protein_name()" , nativeQuery = true)
	public List<String> getProteinNames();
}
