package ca.sheridancollege.controllers;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ca.sheridancollege.FileUploadUtil;
import ca.sheridancollege.beans.Chef;
import ca.sheridancollege.beans.EndUser;
import ca.sheridancollege.beans.Instruction;
import ca.sheridancollege.beans.MessageSystem;
import ca.sheridancollege.beans.Recipe;
import ca.sheridancollege.email.Email;
import ca.sheridancollege.repositories.ChefRepository;
import ca.sheridancollege.repositories.CountryRepository;
import ca.sheridancollege.repositories.CuisineRepository;
import ca.sheridancollege.repositories.DietRepository;
import ca.sheridancollege.repositories.EndUserRepository;
import ca.sheridancollege.repositories.MealTypeRepository;
import ca.sheridancollege.repositories.MeasurementRepository;
import ca.sheridancollege.repositories.MessageRepository;
import ca.sheridancollege.repositories.ProteinRepository;
import ca.sheridancollege.repositories.RecipeIngredientRepository;
import ca.sheridancollege.repositories.RecipeRepository;
import ca.sheridancollege.repositories.RoleRepository;
import ca.sheridancollege.repositories.UserRepository;

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

	@Autowired
	private EndUserRepository endUserRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private ChefRepository chefRepo;

	@Autowired
	private RecipeIngredientRepository recipeIngredientRepo;

	@Autowired
	private Email email;
	
	@Autowired
	private MessageRepository mssgRepo;

	@GetMapping("/")
	public String home(Model model) {
		return "home.html";
	}

	@GetMapping("/login")
	public String toLoginPage(Model model) {

		return "loginPage.html";
	}

	
	@GetMapping("/users/userHome")
	public String UserHome(Model model, Authentication auth){
		
		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());
		List<MessageSystem> mssgs = user.getMessages();
		Collections.reverse(mssgs);
		System.out.println(emailCount);
		model.addAttribute("user", user);
		model.addAttribute("messages", mssgs);
		model.addAttribute("emails", emailCount);
		model.addAttribute("countries", countryRepo.findTop5ByOrderById());
		model.addAttribute("diets", dietRepo.findAll());
		model.addAttribute("meals", mealRepo.findAll());	
		model.addAttribute("suggest", recipeRepo.suggestRecipes(10));

				
		return "/users/userHome.html";
	}
	
	@GetMapping("/users/suggest")
	public String SuggestPage(Model model){
		
		model.addAttribute("countries", recipeRepo.suggestCountry(10));
		model.addAttribute("cuisines", recipeRepo.suggestCuisine(10));
		model.addAttribute("diets", recipeRepo.suggestDiet(10));
		model.addAttribute("proteins", recipeRepo.suggestProtein(10));	
		model.addAttribute("suggest", recipeRepo.suggestRecipes(10));
		
		return "/users/suggestRecipes.html";
	}
	
	@GetMapping("/access-denied")
	public String toAccessDenied() {
		return "/error/access-denied.html";
	}

	@GetMapping("/chefs/uploadRecipe")
	public String goUploadRecipe(Model model, Authentication auth) {
		model.addAttribute("recipe", new Recipe());
		model.addAttribute("countries", countryRepo.findByOrderByName());
		model.addAttribute("cuisines", cuisineRepo.findByOrderByCuisineName());
		model.addAttribute("meals", mealRepo.findAll());
		model.addAttribute("diets", dietRepo.findAll());
		Chef chef = chefRepo.findByEnduser_Email(auth.getName());
		model.addAttribute("chef", chef);

		return "/chefs/recipe.html";
	}

	@PostMapping("/chefs/addRecipe")
	public String addRecipe(@ModelAttribute Recipe recipe, @RequestParam("image") MultipartFile multipartFile, @RequestParam String prep,
			@RequestParam String cook, Model model, @RequestParam int chefId) {

		Chef chef = chefRepo.findById(Long.valueOf(chefId)).get();

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

		if (!validationErrors.isEmpty()) {

			// some errors have occurred
			List<String> errors = new ArrayList<String>();
			for (ConstraintViolation<Recipe> e : validationErrors) {
				errors.add(e.getPropertyPath() + "::" + e.getMessage());
			}
			model.addAttribute("errorMessage", errors);
			model.addAttribute("recipe", new Recipe());
			model.addAttribute("countries", countryRepo.findByOrderByName());
			model.addAttribute("cuisines", cuisineRepo.findByOrderByCuisineName());
			model.addAttribute("meals", mealRepo.findAll());
			model.addAttribute("diets", dietRepo.findAll());
			return "/chefs/recipe.html";
		}

		recipe.setChef(chef);
		Recipe savedRecipe = recipeRepo.save(recipe);

		String fileName = savedRecipe.getId() + StringUtils.cleanPath(multipartFile.getOriginalFilename());
		// user.setPhotos(fileName);

		savedRecipe.setRecipeImg(fileName);
		String uploadDir = "src\\main\\resources\\static\\images\\recipes";
		try {
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		recipeRepo.save(savedRecipe);
		chef.getRecipes().add(savedRecipe);
		chefRepo.save(chef);

		model.addAttribute("recipeId", savedRecipe.getId());
		model.addAttribute("measurements", measureRepo.findAll());
		model.addAttribute("proteins", proteinRepo.findAll());

		return "/chefs/ingredient.html";

	}

	@GetMapping("/chefs/addInstructions/{recipeId}")
	public String addInstructions(@PathVariable int recipeId, Model model) {
		model.addAttribute("recipeId", recipeId);
		return "/chefs/instruction.html";
	}

	@GetMapping("/users/viewAllRecipe")
	public String viewAllRecipes(Model model) {
		model.addAttribute("recipes", recipeRepo.findByAuthTrue());
		return "/users/viewAllRecipes.html";
	}

	@GetMapping("/users/viewRecipe/{recipeId}")
	public String viewRecipe(@PathVariable int recipeId, Model model) {
		model.addAttribute("recipe", recipeRepo.findById(Long.valueOf(recipeId)).get());
		model.addAttribute("rating",20);
		System.out.println("Rating"+recipeRepo.findById(Long.valueOf(recipeId)).get().getRating());
		List<Instruction> instruct = recipeRepo.findById(Long.valueOf(recipeId)).get().getInstructions();
		instruct.sort(Comparator.comparing(Instruction::getStepNumber));
		model.addAttribute("instructions", instruct);

		return "/users/viewRecipe.html";
	}

	@GetMapping("/users/editRecipePartOne/{recipeId}")
	public String editRecipe1(@PathVariable int recipeId, Model model, Authentication auth) {
		model.addAttribute("recipe", recipeRepo.findById(Long.valueOf(recipeId)).get());
		// List<Instruction> instruct = recipeRepo.findById(Long.valueOf(recipeId)).get().getInstructions();
		// instruct.sort(Comparator.comparing(Instruction::getStepNumber));
		// model.addAttribute("instructions", instruct);
		// model.addAttribute("recipe", new Recipe());
		Recipe recipe = recipeRepo.findById(Long.valueOf(recipeId)).get();
		recipe.setAuth(false);

		model.addAttribute("countries", countryRepo.findByContryName(recipe.getCountry().getName()));
		model.addAttribute("meals", mealRepo.findByMealName(recipe.getMealtype().getMealName()));

		if (recipe.getCuisine() != null) {
			model.addAttribute("cuisines", cuisineRepo.findByCuisineName(recipe.getCuisine().getCuisineName()));
		} else {
			model.addAttribute("cuisines", cuisineRepo.findByOrderByCuisineName());
		}

		if (recipe.getDiet() != null) {
			model.addAttribute("diets", dietRepo.findByDietName(recipe.getDiet().getDietType()));
		} else {
			model.addAttribute("diets", dietRepo.findAll());
		}
		float prepTime = recipe.getPrepTime();
		int hours = (int) prepTime / 60;
		int mints = (int) prepTime - (60 * hours);
		DecimalFormat formatter = new DecimalFormat("00");
		String houreFormatted = formatter.format(hours);
		String minFormatted = formatter.format(mints);
		String prepTimeString = houreFormatted.concat(":").concat(minFormatted);
		model.addAttribute("prep", prepTimeString);
		float cookTime = recipe.getCookTime();
		int hoursCook = (int) cookTime / 60;
		int mintsCook = (int) cookTime - (60 * hours);
		DecimalFormat cookFormatter = new DecimalFormat("00");
		String houreCookFormatted = cookFormatter.format(hoursCook);
		String minCookFormatted = cookFormatter.format(mintsCook);
		String cookTimeString = houreCookFormatted.concat(":").concat(minCookFormatted);
		model.addAttribute("cook", cookTimeString);
		Chef chef = chefRepo.findByEnduser_Email(auth.getName());
		model.addAttribute("chef", chef);
		model.addAttribute("recipeId", recipe.getId());
		model.addAttribute("recipeInstructions", recipe.getInstructions());
		model.addAttribute("recipeIngrediants", recipe.getIngredients());

		return "/chefs/editRecipePartOne.html";
	}

	@PostMapping("/chefs/editRecipe")
	public String editRecipe(@ModelAttribute Recipe recipe, @RequestParam("image") MultipartFile multipartFile, @RequestParam String prep,
			@RequestParam String cook, Model model, @RequestParam int chefId, @RequestParam int recipeId) {

		Chef chef = chefRepo.findById(Long.valueOf(chefId)).get();
		Recipe recipeUpdated = recipeRepo.findById(Long.valueOf(recipeId)).get();
		System.out.println("Recipe ***" + recipeId);
		String ptime[] = prep.split(":");
		float phr = Float.parseFloat(ptime[0]) * 60;
		float pmin = Float.parseFloat(ptime[1]);
		recipeUpdated.setPrepTime(phr + pmin);

		String ctime[] = cook.split(":");
		float chr = Float.parseFloat(ctime[0]) * 60;
		float cmin = Float.parseFloat(ctime[1]);
		recipeUpdated.setCookTime(chr + cmin);
		// recipe.setId(Long.valueOf(recipeId));
		// recipe.setChef(chef);
		// recipe.setInstructions(recipeInstructions);

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

		Set<ConstraintViolation<Recipe>> validationErrors = validator.validate(recipe);

		if (!validationErrors.isEmpty()) {

			// some errors have occurred
			List<String> errors = new ArrayList<String>();
			for (ConstraintViolation<Recipe> e : validationErrors) {
				errors.add(e.getPropertyPath() + "::" + e.getMessage());
			}
			model.addAttribute("errorMessage", errors);
			model.addAttribute("recipe", recipeRepo.findById(Long.valueOf(recipe.getId())));
			model.addAttribute("countries", countryRepo.findByContryName(recipe.getCountry().getName()));
			model.addAttribute("meals", mealRepo.findByMealName(recipe.getMealtype().getMealName()));

			if (recipe.getCuisine() != null) {
				model.addAttribute("cuisines", cuisineRepo.findByCuisineName(recipe.getCuisine().getCuisineName()));
			} else {
				model.addAttribute("cuisines", cuisineRepo.findByOrderByCuisineName());
			}

			if (recipe.getDiet() != null) {
				model.addAttribute("diets", dietRepo.findByDietName(recipe.getDiet().getDietType()));
			} else {
				model.addAttribute("diets", dietRepo.findAll());
			}
			return "/chefs/editRecipePartOne.html";
		}

		// recipe.setChef(chef);
		recipeUpdated.setCuisine(recipe.getCuisine());
		recipeUpdated.setCountry(recipe.getCountry());
		recipeUpdated.setMealtype(recipe.getMealtype());
		recipeUpdated.setDiet(recipe.getDiet());
		recipeUpdated.setDescription(recipe.getDescription());
		recipeUpdated.setServingSize(recipe.getServingSize());
		recipeUpdated.setTitle(recipe.getTitle());
		
		recipeUpdated.setRecipeImg(recipe.getRecipeImg());

		recipeRepo.save(recipeUpdated);
		
		model.addAttribute("recipeIngredients", recipeUpdated.getIngredients());
		model.addAttribute("measurements", measureRepo.findAll());
		model.addAttribute("proteins", proteinRepo.findAll());
		model.addAttribute("recipeId", recipeUpdated.getId());

		return "/chefs/editRecipePartTwo.html";

	}

	@PostMapping("/users/searchRecipes")
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
			model.addAttribute("recipes", recipeRepo.findByTitleContainingIgnoreCaseOrCountry_nameContainingIgnoreCase(search, search));
		}

		model.addAttribute("searchVal", search);

		return "/users/viewAllRecipes.html";
	}
	
	@GetMapping("/chefs/editInstructions/{id}")
	public String goEditRecipe(@PathVariable long id, Model model) {
		Recipe recipe = recipeRepo.findById(id).get();
		model.addAttribute("recipe", recipe);
		return "/chefs/editInstruction.html";
	}
	
	@GetMapping("/users/viewRecipesByCountry/{name}")
	public String viewRecipesByCountry(@PathVariable String name, Model model) {
		model.addAttribute("recipes", recipeRepo.findByCountry_nameContainingIgnoreCase(name));
		return "/users/viewAllRecipes.html";
	}

	@GetMapping("/users/viewByDiet/{id}")
	public String viewRecipesByDiet(@PathVariable int id, Model model) {
		model.addAttribute("recipes", recipeRepo.findByDiet_Id(Long.valueOf(id)));
		return "/users/viewAllRecipes.html";
	}

	@GetMapping("/users/viewByMeal/{id}")
	public String viewRecipesByMeal(@PathVariable int id, Model model) {
		model.addAttribute("recipes", recipeRepo.findByMealtype_id(Long.valueOf(id)));
		return "/users/viewAllRecipes.html";
	}

	@GetMapping("/chefs/chefIndex")
	public String chefIndex(Model model, Authentication auth) {
		Chef chef = chefRepo.findByEnduser_Email(auth.getName());
		model.addAttribute("chef", chef);
		return "/chefs/chefIndex";
	}
	
	@GetMapping("/chefs/viewRecipe/{recipeId}")
	public String viewChefRecipe(@PathVariable int recipeId, Model model) {
		model.addAttribute("recipe", recipeRepo.findById(Long.valueOf(recipeId)).get());
		model.addAttribute("rating", recipeRepo.findById(Long.valueOf(recipeId)).get().getRating());
		List<Instruction> instruct = recipeRepo.findById(Long.valueOf(recipeId)).get().getInstructions();
		instruct.sort(Comparator.comparing(Instruction::getStepNumber));
		model.addAttribute("instructions", instruct);

		return "/chefs/viewRecipe.html";
	}

	// hi this is a test
	@GetMapping("/users/discover")
	public String getMap() {
		return "/users/map.html";
	}
	
	@GetMapping("/awaitApproval/{recipeId}")
	public String awaitApproval(@PathVariable int recipeId, Model model) {
		model.addAttribute("recipe", recipeRepo.findById(Long.valueOf(recipeId)).get());
		Recipe recipe = recipeRepo.findById(Long.valueOf(recipeId)).get();
		model.addAttribute("ingredients", recipe.getIngredients());

		
		return "/chefs/awaitApproval";
	}
	
	@GetMapping("/review")
	public String review() {
		return "/chefs/reviews.html";
	}
	
}
