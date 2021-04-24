package ca.sheridancollege.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RecipeMarker {

	private Country country;
	
	private List<RecipeDescription> recipes = new ArrayList<RecipeDescription>();
}
