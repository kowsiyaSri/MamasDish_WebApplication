package ca.sheridancollege.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

}
