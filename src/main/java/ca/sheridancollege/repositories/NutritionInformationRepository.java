package ca.sheridancollege.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.beans.NutritionInformation;

public interface NutritionInformationRepository extends JpaRepository<NutritionInformation, Long> {

}