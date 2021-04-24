package ca.sheridancollege.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RecipeDescription {
	
	private Long recipeId;
	
	private String recipeTitle;
}
