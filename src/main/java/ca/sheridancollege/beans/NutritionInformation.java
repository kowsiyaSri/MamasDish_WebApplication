package ca.sheridancollege.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@Entity
@NoArgsConstructor
public class NutritionInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private float totalFat;

	private float saturatedFat;

	private float cholesterol;

	private float sodium;

	private float totalCarbohydrate;

	private float dietaryFiber;

	private float sugars;

	private float protein;
	
	private float calories;
	
	@OneToOne
	private Recipe recipe;

}
