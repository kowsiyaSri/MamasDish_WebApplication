package ca.sheridancollege.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.beans.Continent;

public interface EndUserContinents extends JpaRepository<Continent, Long> {

}
