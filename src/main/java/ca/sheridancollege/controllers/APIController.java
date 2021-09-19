package ca.sheridancollege.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.beans.Country;
import ca.sheridancollege.beans.Ingredient;
import ca.sheridancollege.beans.Instruction;
import ca.sheridancollege.beans.Measurement;
import ca.sheridancollege.beans.Protein;
import ca.sheridancollege.beans.Recipe;
import ca.sheridancollege.beans.RecipeDescription;
import ca.sheridancollege.beans.RecipeIngredient;
import ca.sheridancollege.beans.RecipeMarker;
import ca.sheridancollege.email.Email;
import ca.sheridancollege.repositories.CountryRepository;
import ca.sheridancollege.repositories.IngredientRepository;
import ca.sheridancollege.repositories.InstructionRepository;
import ca.sheridancollege.repositories.MeasurementRepository;
import ca.sheridancollege.repositories.ProteinRepository;
import ca.sheridancollege.repositories.RecipeIngredientRepository;
import ca.sheridancollege.repositories.RecipeRepository;

@RestController
@RequestMapping("/mamasdish")
public class APIController {

	@Autowired
	@Lazy
	private RecipeRepository recipeRepo;

	@Autowired
	@Lazy
	private IngredientRepository ingredientRepo;

	@Autowired
	@Lazy
	private Email email;

	@Autowired
	@Lazy
	private RecipeIngredientRepository recipeIngredientRepo;

	@Autowired
	@Lazy
	private MeasurementRepository measurementRepo;

	@Autowired
	@Lazy
	private ProteinRepository proteinRepo;

	@Autowired
	@Lazy
	private InstructionRepository instructionRepo;

	@Autowired
	@Lazy
	private CountryRepository countryRepo;

	@GetMapping(value = "/addIngredient/{ingredient}/{quantity}/{measurement}/{recipeId}/{proteinId}")
	public int addIngredient(@PathVariable String ingredient, @PathVariable int quantity, @PathVariable int measurement,
			@PathVariable int recipeId, @PathVariable int proteinId) {

		Ingredient ingred = ingredientRepo.findByIngredientName(ingredient);
		Measurement recipeMeasurement = null;
		Recipe newRecipe = recipeRepo.findById(Long.valueOf(recipeId)).get();
		Protein ingProtein = null;

		if (measurement != 0) {
			recipeMeasurement = measurementRepo.findById(Long.valueOf(measurement)).get();
		}

		if (proteinId != 0) {
			ingProtein = proteinRepo.findById(Long.valueOf(proteinId)).get();
		}

		if (ingred == null) {

			ingred = new Ingredient();
			ingred.setIngredientName(ingredient);
			ingred.setProtein(ingProtein);

			ingred = ingredientRepo.save(ingred);

		}

		RecipeIngredient recipeIngred = new RecipeIngredient().builder().ingredient(ingred).quantity(quantity)
				.measurement(recipeMeasurement).recipe(newRecipe).build();

		newRecipe.getIngredients().add(recipeIngred);
		recipeRepo.save(newRecipe);

		return 1;
	}

	/*
	 * @DeleteMapping(value = "/deleteIngredient/{recipeId}") public int deleteIngredient( @PathVariable int recipeId) {
	 * 
	 * recipeIngredientRepo.deleteIngredientsRecords(Long.valueOf(recipeId));
	 * 
	 * return 1; }
	 */

	@RequestMapping(value = "/deleteIngredient/{recipeId}", method = { RequestMethod.DELETE, RequestMethod.GET })
	public int deleteIngredient(@PathVariable int recipeId) {
		recipeIngredientRepo.deleteIngredientsRecords(Long.valueOf(recipeId));
		return 1;
	}

	@PostMapping(value = "/addInstructions/{recipeId}", headers = { "Content-type=application/json" })
	public int addInstruction(@RequestBody Instruction instruction, @PathVariable int recipeId) {
		System.out.println(instruction);
		Instruction saveInstruction = instructionRepo.save(instruction);
		Recipe recipe = recipeRepo.findById(Long.valueOf(recipeId)).get();
		recipe.getInstructions().add(saveInstruction);
		recipeRepo.save(recipe);
		return 1;
	}

	@GetMapping("/instructions")
	public List<Instruction> listInstructions() {
		return instructionRepo.findAll();
	}

	@GetMapping("/countries")
	public List<Country> getCoordinates() {
		return countryRepo.findAll();
	}

	@GetMapping(value = "/admin/approveRecipe/{id}")
	public int approveRecipe(Model model, @PathVariable int id) {

		Recipe recipe = recipeRepo.findById(Long.valueOf(id)).get();

		recipe.setAuth(true);
		recipeRepo.save(recipe);

		return 1;
	}

	@GetMapping(value = "/admin/approvalRequest/{id}")
	public int sendAuthEmail(Model model, @PathVariable int id) {

		Recipe recipe = recipeRepo.findById(Long.valueOf(id)).get();

		String chefEmail = recipe.getChef().getEnduser().getEmail();
		String subject = "Thank You from Mamas Dish";
		String body = "Thank you for adding your authentic recipe to Mamas Dish.";
		body += "Please allow 24-48 hrs for approval from our authentication team.";

		email.sendEmail(chefEmail, subject, body);

		return 1;
	}

	@GetMapping(value = "/admin/RecipeApproval/{id}")
	public int sendApprovalEmail(Model model, @PathVariable int id) {

		Recipe recipe = recipeRepo.findById(Long.valueOf(id)).get();

		String chefEmail = recipe.getChef().getEnduser().getEmail();
		String subject = recipe.getTitle() + " has been Approved!";
		String body = "Your recipe has now been approved!";

		email.sendEmail(chefEmail, subject, body);

		return 1;
	}

	@GetMapping("/countryRecipes")
	public List<RecipeMarker> countryRecipes() {
		List<Country> countries = countryRepo.findAll();
		List<RecipeMarker> recipeMarkers = new ArrayList<RecipeMarker>();
		for (Country c : countries) {
			RecipeMarker marker = new RecipeMarker();
			marker.setCountry(c);
			List<Recipe> recipes = recipeRepo.findByCountry_nameContainingIgnoreCase(c.getName());
			Collections.shuffle(recipes);
			for (Recipe r : recipes) {
				RecipeDescription description = new RecipeDescription();
				description.setRecipeId(r.getId());
				description.setRecipeTitle(r.getTitle());
				description.setRecipeImg(r.getRecipeImg());
				marker.getRecipes().add(description);
			}
			recipeMarkers.add(marker);
		}
		return recipeMarkers;
	}
}
