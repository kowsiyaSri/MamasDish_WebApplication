package ca.sheridancollege.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.beans.Protein;

public interface ProteinRepository extends JpaRepository<Protein, Long> {

}
