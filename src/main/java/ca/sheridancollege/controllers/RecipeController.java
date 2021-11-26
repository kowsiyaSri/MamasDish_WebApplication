package ca.sheridancollege.controllers;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
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

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import ca.sheridancollege.beans.Chef;
import ca.sheridancollege.beans.Continent;
import ca.sheridancollege.beans.EndUser;
import ca.sheridancollege.beans.Instruction;
import ca.sheridancollege.beans.MessageSystem;
import ca.sheridancollege.beans.Rating;
import ca.sheridancollege.beans.Recent;
import ca.sheridancollege.beans.Recipe;
import ca.sheridancollege.beans.Role;
import ca.sheridancollege.beans.User;
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
import ca.sheridancollege.repositories.RatingRepository;
import ca.sheridancollege.repositories.RecentRepository;
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
	private RecentRepository recentRepo;

	@Autowired
	private RecipeIngredientRepository recipeIngredientRepo;

	@Autowired
	private Email email;

	@Autowired
	private MessageRepository mssgRepo;

	@Autowired
	private RatingRepository ratingRepo;

	@GetMapping("/")
	public String home(Model model) {
		return "home.html";
	}

	@GetMapping("/login")
	public String toLoginPage(Model model) {

		return "loginPage.html";
	}

	// checks user role and return the corresponding home page
	@GetMapping("/landingPage")
	public String HomePage(Authentication auth) {

		User user = userRepo.findByUsername(auth.getName());
		boolean isAdmin = false;
		boolean isChef = false;

		for (Role role : user.getRoles()) {
			if (role.getRolename().equals("ROLE_ADMIN")) {
				isAdmin = true;
			} else if (role.getRolename().equals("ROLE_CHEF")) {
				isChef = true;
			}
		}

		if (isAdmin) {
			return "redirect:/admin";
		} else if (isChef) {
			return "redirect:/chefs/chefIndex";
		} else {
			return "redirect:/users/userHome";
		}
	}

	@GetMapping("/users/userHome")
	public String UserHome(Model model, Authentication auth) {

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
		model.addAttribute("suggest", recipeRepo.suggestRecipes(user.getId()));

		return "users/userHome.html";
	}

	@GetMapping("/users/suggest")
	public String SuggestPage(Model model, Authentication auth) {

		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());
		Long id = user.getId();
		model.addAttribute("countries", recipeRepo.suggestCountry(id));
		model.addAttribute("cuisines", recipeRepo.suggestCuisine(id));
		model.addAttribute("diets", recipeRepo.suggestDiet(id));
		model.addAttribute("proteins", recipeRepo.suggestProtein(id));
		model.addAttribute("suggest", recipeRepo.suggestRecipes(id));
		model.addAttribute("emails", emailCount);

		return "users/suggestRecipes.html";
	}

	@GetMapping("/access-denied")
	public String toAccessDenied() {
		return "error/access-denied.html";
	}

	@GetMapping("/chefs/uploadRecipe")
	public String goUploadRecipe(Model model, Authentication auth) {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());

		model.addAttribute("recipe", new Recipe());
		model.addAttribute("countries", countryRepo.findByOrderByName());
		model.addAttribute("cuisines", cuisineRepo.findByOrderByCuisineName());
		model.addAttribute("meals", mealRepo.findAll());
		model.addAttribute("diets", dietRepo.findAll());
		Chef chef = chefRepo.findByEnduser_Email(auth.getName());
		model.addAttribute("chef", chef);
		model.addAttribute("emails", emailCount);

		return "chefs/recipe.html";
	}

	@PostMapping("/chefs/addRecipe")
	public String addRecipe(@ModelAttribute Recipe recipe, @RequestParam("image") MultipartFile multipartFile,
			@RequestParam String prep, @RequestParam String cook, Model model, Authentication auth,
			@RequestParam int chefId) throws JSchException, SftpException, IOException {

		Chef chef = chefRepo.findById(Long.valueOf(chefId)).get();
		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());

		model.addAttribute("emails", emailCount);

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
			return "chefs/recipe.html";
		}

		recipe.setChef(chef);
		Recipe savedRecipe = recipeRepo.save(recipe);

		String fileName = savedRecipe.getId() + StringUtils.cleanPath(multipartFile.getOriginalFilename());

		savedRecipe.setRecipeImg(fileName);

		String remoteDir = "public_html/images/recipes/";
		InputStream inputStream = new BufferedInputStream(multipartFile.getInputStream());

		ChannelSftp channelSftp = null;
		try {
			channelSftp = setupJsch();
		} catch (JSchException e) {
			System.out.println(e);
		}
		try {
			channelSftp.connect();
		} catch (JSchException e) {
			System.out.println(e);
		}
		try {
			channelSftp.put(inputStream, remoteDir + fileName);
			System.out.println("Upload Complete");
		} catch (SftpException e) {
			System.out.println(e);
		}
		channelSftp.exit();

		recipeRepo.save(savedRecipe);
		chef.getRecipes().add(savedRecipe);
		chefRepo.save(chef);

		model.addAttribute("recipeId", savedRecipe.getId());
		model.addAttribute("measurements", measureRepo.findAll());
		model.addAttribute("proteins", proteinRepo.findAll());

		return "chefs/ingredient.html";

	}

	private ChannelSftp setupJsch() throws JSchException {

		String remoteHost = "dev.fast.sheridanc.on.ca";
		String username = "ocranp";
		String password = "8NEQmQ*!un6";

		JSch jsch = new JSch();
		Session jschSession = jsch.getSession(username, remoteHost);
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		jschSession.setConfig(config);
		jschSession.setPassword(password);
		jschSession.connect();
		return (ChannelSftp) jschSession.openChannel("sftp");
	}

	@GetMapping("/chefs/addInstructions/{recipeId}")
	public String addInstructions(@PathVariable int recipeId, Model model, Authentication auth) {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());

		model.addAttribute("emails", emailCount);
		model.addAttribute("recipeId", recipeId);
		return "chefs/instruction.html";
	}

	@GetMapping("/users/viewAllRecipe")
	public String viewAllRecipes(Model model, Authentication auth) {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());

		model.addAttribute("emails", emailCount);
		model.addAttribute("recipes", recipeRepo.findByAuthTrue());
		model.addAttribute("countries", countryRepo.getCountryNames());
		model.addAttribute("proteins", proteinRepo.getProteinNames());
		model.addAttribute("diets", dietRepo.getDietNames());
		return "users/viewAllRecipes.html";
	}

	@PostMapping("/users/filterResults")
	public String viewFilterResults(Model model, Authentication auth,
			@RequestParam(required = false, value = "countries[]") String[] countries,
			@RequestParam(required = false, value = "diets[]") String[] diets,
			@RequestParam(required = false, value = "proteins[]") String[] proteins,
			@RequestParam(required = false) int cal1, @RequestParam(required = false) int cal2) {

		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());
		model.addAttribute("emails", emailCount);

		String countryString = "";
		String dietString = "";
		String proteinString = "";

		if (countries != null) {
			for (int i = 0; i < countries.length - 1; i++) {
				countryString += "\'" + countries[i] + "\',";
			}
			countryString += "\'" + countries[countries.length - 1] + "\'";

		}

		if (diets != null) {
			for (int i = 0; i < diets.length - 1; i++) {
				dietString += "\'" + diets[i] + "\',";
			}
			dietString += "\'" + diets[diets.length - 1] + "\'";
		}

		if (proteins != null) {
			for (int i = 0; i < proteins.length - 1; i++) {
				proteinString += "\'" + proteins[i] + "\',";
			}
			proteinString += "\'" + proteins[proteins.length - 1] + "\'";
		}

		model.addAttribute("recipes", recipeRepo.getFilterRecipes(countryString, dietString, proteinString, 0, 0));
		
		model.addAttribute("countries", countryRepo.getCountryNames());
		model.addAttribute("proteins", proteinRepo.getProteinNames());
		model.addAttribute("diets", dietRepo.getDietNames());
		model.addAttribute("countriesChecked", countries);
		model.addAttribute("dietsChecked", diets);
		model.addAttribute("proteinsChecked", proteins);
		model.addAttribute("cal1", cal1);
		model.addAttribute("cal2", cal2);
		 

		return "users/viewAllRecipes.html";
	}

	@GetMapping("/users/viewRecipe/{recipeId}")
	public String viewRecipe(@PathVariable int recipeId, Model model, Authentication auth) {

		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());

		model.addAttribute("emails", emailCount);

		double ratingSum = 0;
		double ratingAve = 0;
		List<Rating> listRatings = ratingRepo.findByRecipeId(Long.valueOf(recipeId));
		if (listRatings.size() > 0) {
			for (Rating rating : listRatings) {
				ratingSum += rating.getRating();
			}
			ratingAve = ratingSum / listRatings.size();
		}

		Recipe recipe = recipeRepo.findById(Long.valueOf(recipeId)).get();
		model.addAttribute("recipe", recipe);
		List<Instruction> instruct = recipeRepo.findById(Long.valueOf(recipeId)).get().getInstructions();
		instruct.sort(Comparator.comparing(Instruction::getStepNumber));
		model.addAttribute("instructions", instruct);
		model.addAttribute("rating", ratingAve);
		model.addAttribute("reviews", listRatings);

		// check if in recent recipe
		boolean isPresent = false;
		Recent present = null;
		for (Recent r : user.getRecent()) {
			if (r.getRecipe().getId() == recipe.getId()) {
				isPresent = true;
				present = r;
			}
		}
		List<Continent> continents = user.getContinents();
	
		
		
		boolean inCont = false;
		for(Continent cont : continents) {
			if (recipe.getCountry().getContinent() == cont) {
				inCont = true;
			}
		}
		

			model.addAttribute("canAuthenticate", inCont);
		// Gets current date
		long millis = System.currentTimeMillis();
		Date now = new Date(millis);

		if (!isPresent) {
			Recent recent = Recent.builder().recipe(recipe).date(now).build();

			// adds recent to user list
			if (user.getRecent().size() <= 15) {
				Recent saved = recentRepo.save(recent);
				user.getRecent().add(saved);
				endUserRepo.save(user);
			} else {

				// will remove the first item from the list and add new one to the end
				Long remRec = user.getRecent().get(0).getId();
				user.getRecent().remove(0);
				recentRepo.deleteById(remRec);

				// adds to list
				Recent saved = recentRepo.save(recent);
				user.getRecent().add(saved);
				endUserRepo.save(user);
			}
		} else {

			// update date on recent
			present.setDate(now);
			recentRepo.save(present);

			user.getRecent().sort(Comparator.comparing(r -> r.getDate()));
			endUserRepo.save(user);
		}

		// checks if is saved
		boolean isSaved = false;
		for (Recipe r : user.getRecipe()) {
			if (r.getId() == recipe.getId()) {
				isSaved = true;
			}
		}
		
	
		model.addAttribute("saved", isSaved);
			
		return "users/viewRecipe.html";
	}

	// view recent recipe page
	@GetMapping("/users/viewRecent")
	public String viewRecent(Model model, Authentication auth) {

		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());

		model.addAttribute("user", user);
		model.addAttribute("emails", emailCount);

		return "users/recent.html";
	}

	// save recipe
	@GetMapping("/users/saveRecipe/{recipeId}")
	public String saveRecipe(Model model, Authentication auth, @PathVariable int recipeId) {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		Recipe recipe = recipeRepo.findById(Long.valueOf(recipeId)).get();

		boolean isPresent = false;
		for (Recipe r : user.getRecipe()) {
			if (r.getId() == recipe.getId()) {
				isPresent = true;
			}
		}

		if (!isPresent) {
			user.getRecipe().add(recipe);
			endUserRepo.save(user);
		}

		return "redirect:/users/viewRecipe/" + recipeId;
	}

	// un-saved recipe
	@GetMapping("/users/removeRecipe/{recipeId}")
	public String unsaveRecipe(Model model, Authentication auth, @PathVariable int recipeId) {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		Recipe recipe = recipeRepo.findById(Long.valueOf(recipeId)).get();

		boolean isPresent = false;
		Recipe remove = null;
		for (Recipe r : user.getRecipe()) {
			if (r.getId() == recipe.getId()) {
				isPresent = true;
				remove = r;
			}
		}

		if (isPresent) {
			user.getRecipe().remove(remove);
			endUserRepo.save(user);
		}

		return "redirect:/users/viewRecipe/" + recipeId;
	}

	// view saved recipe
	@GetMapping("/users/viewSaved")
	public String viewSaved(Model model, Authentication auth) {

		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());

		model.addAttribute("user", user);
		model.addAttribute("emails", emailCount);

		return "users/saved.html";
	}

	// delete a recipe from a chefs portal
	@GetMapping("/users/deleteRecipe/{recipeId}")
	public String deleteRecipe1(@PathVariable int recipeId, Model model, Authentication auth) {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		Chef chef = chefRepo.findByEnduser_Email(auth.getName());
		model.addAttribute("chef", chef);
		recipeRepo.deleteRecipe(Long.valueOf(recipeId));
		return "redirect:/chefs/chefIndex";
	}

	@GetMapping("/users/editRecipePartOne/{recipeId}")
	public String editRecipe1(@PathVariable int recipeId, Model model, Authentication auth) {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());

		model.addAttribute("emails", emailCount);

		model.addAttribute("recipe", recipeRepo.findById(Long.valueOf(recipeId)).get());
		Recipe recipe = recipeRepo.findById(Long.valueOf(recipeId)).get();
		recipe.setAuth(false);

		model.addAttribute("countries", countryRepo.findAll());
		model.addAttribute("meals", mealRepo.findAll());
		model.addAttribute("cuisines", cuisineRepo.findByOrderByCuisineName());
		model.addAttribute("diets", dietRepo.findAll());

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

		return "chefs/editRecipePartOne.html";
	}

	@PostMapping("/chefs/editRecipe")
	public String editRecipe(@ModelAttribute Recipe recipe, @RequestParam("image") MultipartFile multipartFile,
			@RequestParam String prep, @RequestParam String cook, Model model, Authentication auth,
			@RequestParam int chefId, @RequestParam int recipeId) throws IOException {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());

		model.addAttribute("emails", emailCount);

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
			return "chefs/editRecipePartOne.html";
		}

		System.out.println(multipartFile.getOriginalFilename());

		if (!multipartFile.getOriginalFilename().isEmpty()) {
			String fileName = recipeUpdated.getId() + StringUtils.cleanPath(multipartFile.getOriginalFilename());

			String remoteDir = "public_html/images/recipes/";
			InputStream inputStream = new BufferedInputStream(multipartFile.getInputStream());

			ChannelSftp channelSftp = null;
			try {
				channelSftp = setupJsch();
			} catch (JSchException e) {
				System.out.println(e);
			}
			try {
				channelSftp.connect();
			} catch (JSchException e) {
				System.out.println(e);
			}
			try {
				channelSftp.put(inputStream, remoteDir + fileName);
				System.out.println("Upload Complete");
			} catch (SftpException e) {
				System.out.println(e);
			}
			channelSftp.exit();

			recipeUpdated.setRecipeImg(fileName);
		}

		// recipe.setChef(chef);
		recipeUpdated.setCuisine(recipe.getCuisine());
		recipeUpdated.setCountry(recipe.getCountry());
		recipeUpdated.setMealtype(recipe.getMealtype());
		recipeUpdated.setDiet(recipe.getDiet());
		recipeUpdated.setDescription(recipe.getDescription());
		recipeUpdated.setServingSize(recipe.getServingSize());
		recipeUpdated.setTitle(recipe.getTitle());

		recipeRepo.save(recipeUpdated);

		model.addAttribute("recipeIngredients", recipeUpdated.getIngredients());
		model.addAttribute("measurements", measureRepo.findAll());
		model.addAttribute("proteins", proteinRepo.findAll());
		model.addAttribute("recipeId", recipeUpdated.getId());

		return "chefs/editRecipePartTwo.html";

	}

	@GetMapping("/users/searchRecipes")
	public String searchRecipesAll(Model model, Authentication auth, @RequestParam String search,
			@RequestParam int searchBy) {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());

		model.addAttribute("emails", emailCount);

		switch (searchBy) {
		case 0:
			model.addAttribute("recipes", recipeRepo.basicSearch(search));
			break;
		case 1:
			model.addAttribute("recipes", recipeRepo.findByTitleContainingIgnoreCase(search));
			break;
		case 3:
			model.addAttribute("recipes", recipeRepo.findByCountry_nameContainingIgnoreCase(search));
			break;
		case 2:
			model.addAttribute("recipes",
					recipeRepo.findByIngredients_Ingredient_IngredientNameContainingIgnoreCase(search));
			break;

		default:
			model.addAttribute("recipes",
					recipeRepo.findByTitleContainingIgnoreCaseOrCountry_nameContainingIgnoreCase(search, search));
		}

		model.addAttribute("searchVal", search);

		return "users/viewAllRecipes.html";
	}

	@PostMapping("/users/searchRecipes")
	public String searchRecipes(Model model, Authentication auth, @RequestParam String search,
			@RequestParam int searchBy) {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());

		model.addAttribute("emails", emailCount);

		switch (searchBy) {
		case 0:
			model.addAttribute("recipes", recipeRepo.basicSearch(search));
			break;
		case 1:
			model.addAttribute("recipes", recipeRepo.findByTitleContainingIgnoreCase(search));
			break;
		case 3:
			model.addAttribute("recipes", recipeRepo.findByCountry_nameContainingIgnoreCase(search));
			break;
		case 2:
			model.addAttribute("recipes",
					recipeRepo.findByIngredients_Ingredient_IngredientNameContainingIgnoreCase(search));
			break;

		default:
			model.addAttribute("recipes",
					recipeRepo.findByTitleContainingIgnoreCaseOrCountry_nameContainingIgnoreCase(search, search));
		}

		model.addAttribute("searchVal", search);

		return "users/viewAllRecipes.html";
	}

	@GetMapping("/chefs/editInstructions/{id}")
	public String goEditRecipe(@PathVariable long id, Model model, Authentication auth) {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());

		model.addAttribute("emails", emailCount);

		Recipe recipe = recipeRepo.findById(id).get();
		model.addAttribute("recipe", recipe);
		return "chefs/editInstruction.html";
	}

	@GetMapping("/users/viewRecipesByCountry/{name}")
	public String viewRecipesByCountry(@PathVariable String name, Model model, Authentication auth) {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());

		model.addAttribute("emails", emailCount);

		model.addAttribute("recipes", recipeRepo.findByCountry_nameContainingIgnoreCase(name));
		return "users/viewAllRecipes.html";
	}

	@GetMapping("/users/viewByDiet/{id}")
	public String viewRecipesByDiet(@PathVariable int id, Model model, Authentication auth) {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());

		model.addAttribute("emails", emailCount);

		model.addAttribute("recipes", recipeRepo.findByDiet_Id(Long.valueOf(id)));
		return "users/viewAllRecipes.html";
	}

	@GetMapping("/users/viewByMeal/{id}")
	public String viewRecipesByMeal(@PathVariable int id, Model model, Authentication auth) {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());

		model.addAttribute("emails", emailCount);

		model.addAttribute("recipes", recipeRepo.findByMealtype_id(Long.valueOf(id)));
		return "users/viewAllRecipes.html";
	}

	@GetMapping("/chefs/chefIndex")
	public String chefIndex(Model model, Authentication auth) {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());

		model.addAttribute("emails", emailCount);

		Chef chef = chefRepo.findByEnduser_Email(auth.getName());
		model.addAttribute("chef", chef);
		return "chefs/chefIndex";
	}

	@GetMapping("/chefs/viewRecipe/{recipeId}")
	public String viewChefRecipe(@PathVariable int recipeId, Model model, Authentication auth) {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());

		model.addAttribute("emails", emailCount);

		model.addAttribute("recipe", recipeRepo.findById(Long.valueOf(recipeId)).get());
		List<Instruction> instruct = recipeRepo.findById(Long.valueOf(recipeId)).get().getInstructions();
		instruct.sort(Comparator.comparing(Instruction::getStepNumber));
		model.addAttribute("instructions", instruct);

		return "chefs/viewRecipe.html";
	}

	@GetMapping("/users/discover")
	public String getMap(Model model, Authentication auth) {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());

		model.addAttribute("emails", emailCount);

		return "users/map.html";
	}

	@GetMapping("/awaitApproval/{recipeId}")
	public String awaitApproval(@PathVariable int recipeId, Model model, Authentication auth) {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());

		model.addAttribute("emails", emailCount);

		model.addAttribute("recipe", recipeRepo.findById(Long.valueOf(recipeId)).get());
		Recipe recipe = recipeRepo.findById(Long.valueOf(recipeId)).get();
		recipe.setAuth(false);
		recipe.setComplete(true);
		recipeRepo.save(recipe);
		model.addAttribute("ingredients", recipe.getIngredients());

		return "chefs/awaitApproval";
	}

	@GetMapping("/reviewRecipe/{id}")
	public String reviewRecipe(@PathVariable long id, Model model, Authentication auth) {
		EndUser userEmail = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(userEmail.getId());

		model.addAttribute("emails", emailCount);

		model.addAttribute("recipeId", id);
		User user = userRepo.findByUsername(auth.getName());
		model.addAttribute("userId", user.getId());
		return "users/reviewForm.html";
	}

	@PostMapping("/viewRecipeAfterRating")
	public String veiwRecipeRating(Model model, Authentication auth, @RequestParam float rating,
			@RequestParam String commentText, @RequestParam long userId, @RequestParam long recipeId,
			@RequestParam(value = "anonymous", required = false) String anonymous) {

		EndUser user = endUserRepo.findById(userId).get();
		Recipe recipe = recipeRepo.findById(recipeId).get();

		EndUser userEmail = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(userEmail.getId());

		model.addAttribute("emails", emailCount);

		Rating ratingEntity = Rating.builder().recipe(recipe).user(user).rating(rating).comment(commentText).build();

		if (anonymous == null) {
			ratingEntity.setUserName(user.getFirstName() + " " + user.getLastName());
		} else {
			ratingEntity.setUserName("Mama's Dish User");
		}

		ratingRepo.save(ratingEntity);

		System.out.println("Rating is " + rating);
		System.out.println("Review is " + commentText);
		System.out.println("anonymous " + anonymous);
		model.addAttribute("recipes", recipeRepo.findByAuthTrue());
		return "redirect:/users/viewRecipe/" + recipeId;
	}

}
