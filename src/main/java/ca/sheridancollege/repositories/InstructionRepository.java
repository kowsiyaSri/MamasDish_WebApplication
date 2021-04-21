package ca.sheridancollege.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.beans.Instruction;

public interface InstructionRepository extends JpaRepository<Instruction, Long> {

}
