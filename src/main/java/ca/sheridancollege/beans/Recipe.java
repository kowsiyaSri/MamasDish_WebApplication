package ca.sheridancollege.beans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Recipe {
	
	public Recipe() {
		this.auth = false;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int iconPic;

	private String recipeImg;

	@NotNull(message = "Title is Mandatory.")
	@Size(min = 1, max = 100, message = "Title must be between 1-100 characters.")
	private String title;

	private float duration;

	private float rating;

	@NotNull(message = "Serving size is Mandatory!")
	@Min(value = 1, message = "Serving size muat be atleast 1")
	private int servingSize;

	private float cookTime;

	private float prepTime;
	
	private boolean auth;

	@NotNull(message = "Summary is Mandatory.")
	@Size(min = 1, max = 100, message = "Summary must be at least 1 character in length.")
	@Lob
	private String description;

	@ManyToOne
	@JsonIgnore
	private Chef chef;

	@NotNull(message = "MealType is Mandatory!")
	@OneToOne
	private MealType mealtype;

	@NotNull(message = "Country is Mandatory!")

	@OneToOne
	private Country country;

	@OneToOne
	private Cuisine cuisine;

	@OneToOne
	private Diet diet;

	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
	private List<RecipeIngredient> ingredients;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Instruction> instructions;

	// Variables for nutrition information
	private float calories;

	private float totalFat;

	private float saturatedFat;

	private float cholesterol;

	private float sodium;

	private float totalCarbohydrate;

	private float dietaryFiber;

	private float sugars;

	private float protein;
}
