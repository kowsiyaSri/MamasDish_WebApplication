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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import ca.sheridancollege.beans.Country;
import ca.sheridancollege.beans.Diet;
import ca.sheridancollege.beans.EndUser;
import ca.sheridancollege.beans.Instruction;
import ca.sheridancollege.beans.MealType;
import ca.sheridancollege.beans.Recipe;
import ca.sheridancollege.beans.RecipeIngredient;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.email.Email;
import ca.sheridancollege.repositories.ChefRepository;
import ca.sheridancollege.repositories.CountryRepository;
import ca.sheridancollege.repositories.CuisineRepository;
import ca.sheridancollege.repositories.DietRepository;
import ca.sheridancollege.repositories.EndUserRepository;
import ca.sheridancollege.repositories.MealTypeRepository;
import ca.sheridancollege.repositories.MeasurementRepository;
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
	private RecipeIngredientRepository RecipeIngredientRepo;

	@Autowired
	private Email email;

	@GetMapping("/")
	public String home(Model model) {
		return "home.html";
	}

	@GetMapping("/login")
	public String toLoginPage(Model model) {

		return "loginPage.html";
	}

	@GetMapping("/users/userHome")
	public String UserHome(Model model) {

		// finds list of countries which contain recipes
		List<Country> displayCountries = new ArrayList<Country>();
		for (Country c : countryRepo.findAll()) {
			List<Recipe> recipes = recipeRepo.findByCountry_nameContainingIgnoreCase(c.getName());
			if (recipes.size() > 0) {
				displayCountries.add(c);
			}
		}
		model.addAttribute("countries", displayCountries);

		// finds list of diets which contains recipes
		List<Diet> diets = new ArrayList<Diet>();
		for (Diet d : dietRepo.findAll()) {
			List<Recipe> recipes = recipeRepo.findByDiet_Id(d.getId());
			if (recipes.size() > 0) {
				diets.add(d);
			}
		}
		model.addAttribute("diets", diets);

		// find list of meal types which contain recipes
		List<MealType> meals = new ArrayList<MealType>();
		for (MealType m : mealRepo.findAll()) {
			List<Recipe> recipes = recipeRepo.findByMealtype_id(m.getId());
			if (recipes.size() > 0) {
				meals.add(m);
			}
		}
		model.addAttribute("meals", meals);

		// suggest recipes
		List<Recipe> allRecipes = recipeRepo.findAll();
		Collections.shuffle(allRecipes);
		List<Recipe> suggestRecipes = new ArrayList<Recipe>();
		for (int i = 0; i < 5; i++) {
			suggestRecipes.add(allRecipes.get(i));
		}
		model.addAttribute("suggest", suggestRecipes);
		return "/users/userHome.html";
	}

	private String encodePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

	@PostMapping("/register")
	public String processRegister(@RequestParam String email, @RequestParam String fname, @RequestParam String lname,
			@RequestParam String password, @RequestParam(required = false) boolean isChef,
			@RequestParam(required = false) String description, @RequestParam String password2, Model model) {

		if (!password.equals(password2)) {

			model.addAttribute("errMssg", "Passwords MUST match.");
			model.addAttribute("emailInput", email);
			model.addAttribute("fNameInput", fname);
			model.addAttribute("lNameInput", lname);

			return "register.html";

		} else if (userRepo.findByUsername(email) != null) {

			model.addAttribute("errMssg", "Email already registered.");
			model.addAttribute("emailInput", email);
			model.addAttribute("fNameInput", fname);
			model.addAttribute("lNameInput", lname);

			return "register.html";
		} else {
			EndUser endUser = EndUser.builder().firstName(fname).lastName(lname).email(email).password(password).build();
			User user = new User(email, encodePassword(password));
			endUserRepo.save(endUser);
			if (isChef) {
				Chef chef = Chef.builder().description(description).recipes(new ArrayList<Recipe>()).enduser(endUser).build();
				user.getRoles().add(roleRepo.findByRolename("ROLE_CHEF"));
				chefRepo.save(chef);
			} else {
				user.getRoles().add(roleRepo.findByRolename("ROLE_USER"));

			}

			userRepo.save(user);

			return "home.html";
		}

	}

	@GetMapping("/register")
	public String Register() {
		return "register.html";
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

		model.addAttribute("ings", RecipeIngredientRepo.findByRecipe(Long.valueOf(recipeId)).size());
		model.addAttribute("quan", RecipeIngredientRepo.findQuantity(Long.valueOf(recipeId)));
		model.addAttribute("names", RecipeIngredientRepo.findIngredientName(Long.valueOf(recipeId)));
		model.addAttribute("prots", RecipeIngredientRepo.findProtien(Long.valueOf(recipeId)));
		model.addAttribute("recipeId", recipeUpdated.getId());
		model.addAttribute("recipe", recipeUpdated);
		model.addAttribute("measurements", measureRepo.findAll());
		model.addAttribute("proteins", proteinRepo.findAll());

		System.out.print("Prot" + RecipeIngredientRepo.findProtien(Long.valueOf(recipeId)));

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
		System.out.print(RecipeIngredientRepo.findByRecipe(Long.valueOf(7)));

		System.out.print(RecipeIngredientRepo.findQuantity(Long.valueOf(7)));
		Chef chef = chefRepo.findByEnduser_Email(auth.getName());
		model.addAttribute("chef", chef);
		model.addAttribute("quan", RecipeIngredientRepo.findQuantity(Long.valueOf(7)));
		model.addAttribute("protiens", RecipeIngredientRepo.findProtien(Long.valueOf(7)));
		System.out.print("Prot" + RecipeIngredientRepo.findProtien(Long.valueOf(7)));
		return "/chefs/chefIndex";

	}

	// hi this is a test
	@GetMapping("/users/discover")
	public String getMap() {
		return "/users/map.html";
	}
}
