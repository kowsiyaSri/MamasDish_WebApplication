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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder 
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Recipe {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
    private int iconPic;
    
    private String title;
    
    private float duration;
    
    private float rating;
    
    @OneToMany
    private List<Rating> ratings;
    
    private int servingSize;
    
    private float cookTime;
    
    private float prepTime;
    
    @Lob
    private String description;
    
	@ManyToOne
    private Chef chef;
    
	@OneToOne
    private MealType mealtype;
    
	@OneToOne
    private Country country;    
    
	@OneToOne
    private Cuisine cuisine;
    
	@OneToOne
    private Diet diet;
	
	@OneToMany(mappedBy="recipe", cascade= CascadeType.ALL)
	private List<RecipeIngredient> ingredients;
	
	@OneToMany(cascade= CascadeType.ALL)
	private List<Instruction> instructions;
}
