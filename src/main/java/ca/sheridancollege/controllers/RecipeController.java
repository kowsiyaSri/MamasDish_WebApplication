package ca.sheridancollege.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.Recipe;
import ca.sheridancollege.repositories.CountryRepository;
import ca.sheridancollege.repositories.CuisineRepository;
import ca.sheridancollege.repositories.DietRepository;
import ca.sheridancollege.repositories.MealTypeRepository;
import ca.sheridancollege.repositories.MeasurementRepository;
import ca.sheridancollege.repositories.ProteinRepository;
import ca.sheridancollege.repositories.RecipeRepository;

@Controller
public class RecipeController {

	@Autowired
	private RecipeRepository recipeRepo;

	@Autowired
	private CountryRepository countryRepo;

	@Autowired
	private MealTypeRepository mealRepo;

	@Autowired
	private CuisineRepository cuisineRepo;

	@Autowired
	private DietRepository dietRepo;

	@Autowired
	private ProteinRepository proteinRepo;

	@Autowired
	private MeasurementRepository measureRepo;

	@GetMapping("/")
	public String home(Model model) {
		return "home.html";
	}

	@GetMapping("/uploadRecipe")
	public String goUploadRecipe(Model model) {
		model.addAttribute("recipe", new Recipe());
		model.addAttribute("countries", countryRepo.findByOrderByName());
		model.addAttribute("cuisines", cuisineRepo.findByOrderByCuisineName());
		model.addAttribute("meals", mealRepo.findAll());
		model.addAttribute("diets", dietRepo.findAll());
		return "recipe.html";
	}

	@PostMapping("/addRecipe")
	public String addRecipe(@ModelAttribute Recipe recipe, @RequestParam String prep, @RequestParam String cook,
			Model model) {
		
		String ptime[] = prep.split(":");
		float phr = Float.parseFloat(ptime[0]) * 60;
		float pmin = Float.parseFloat(ptime[1]);
		recipe.setPrepTime(phr + pmin);

		String ctime[] = cook.split(":");
		float chr = Float.parseFloat(ctime[0]) * 60;
		float cmin = Float.parseFloat(ctime[1]);
		recipe.setCookTime(chr + cmin);
		
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		
		Set<ConstraintViolation<Recipe>> validationErrors = validator.validate(recipe);
		
		if(!validationErrors.isEmpty()) {
			
			//some errors have occurred
			List<String> errors = new ArrayList<String>();
			for(ConstraintViolation<Recipe> e : validationErrors) {
				errors.add(e.getPropertyPath() + "::" + e.getMessage());
			}
			model.addAttribute("errorMessage", errors);
			model.addAttribute("recipe", new Recipe());
			model.addAttribute("countries", countryRepo.findByOrderByName());
			model.addAttribute("cuisines", cuisineRepo.findByOrderByCuisineName());
			model.addAttribute("meals", mealRepo.findAll());
			model.addAttribute("diets", dietRepo.findAll());
			return "recipe.html";
		}
		
		Recipe savedRecipe = recipeRepo.save(recipe);

		model.addAttribute("recipeId", savedRecipe.getId());
		model.addAttribute("measurements", measureRepo.findAll());
		model.addAttribute("proteins", proteinRepo.findAll());

		return "ingredient.html";
	}

	@GetMapping("/ingr")
	public String addIngredient(Model model) {
		model.addAttribute("measurements", measureRepo.findAll());
		model.addAttribute("proteins", proteinRepo.findAll());
		return "ingredient.html";
	}

	@GetMapping("/addInstructions/{recipeId}")
	public String addInstructions(@PathVariable int recipeId, Model model) {
		model.addAttribute("recipeId", recipeId);
		return "instruction.html";
	}

	@GetMapping("/viewAllRecipe")
	public String viewAllRecipes(Model model) {
		model.addAttribute("recipes", recipeRepo.findAll());
		return "viewAllRecipes.html";
	}

	@GetMapping("/viewRecipe/{recipeId}")
	public String viewRecipe(@PathVariable int recipeId, Model model) {
		model.addAttribute("recipe", recipeRepo.findById(Long.valueOf(recipeId)).get());
		return "viewRecipe.html";
	}

	@PostMapping("/searchRecipes")
	public String searchRecipes(Model model, @RequestParam String search, @RequestParam int searchBy) {

		switch (searchBy) {

		case 1:
			model.addAttribute("recipes", recipeRepo.findByTitleContainingIgnoreCase(search));
			break;
		case 3:
			model.addAttribute("recipes", recipeRepo.findByCountry_nameContainingIgnoreCase(search));
			break;
		case 2:
			model.addAttribute("recipes", recipeRepo.findByIngredients_Ingredient_IngredientNameContainingIgnoreCase(search));
			break;
			
			default:
				model.addAttribute("recipes",
						recipeRepo.findByTitleContainingIgnoreCaseOrCountry_nameContainingIgnoreCase(search, search));
		}

		model.addAttribute("searchVal", search);
		
		return "viewAllRecipes.html";
	}
}
