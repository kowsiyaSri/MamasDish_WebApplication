package ca.sheridancollege.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.Authentication;
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

import ca.sheridancollege.beans.AuthUserContinent;
import ca.sheridancollege.beans.Country;
import ca.sheridancollege.beans.EndUser;
import ca.sheridancollege.beans.Ingredient;
import ca.sheridancollege.beans.Instruction;
import ca.sheridancollege.beans.Measurement;
import ca.sheridancollege.beans.MessageSystem;
import ca.sheridancollege.beans.NutritionInformation;
import ca.sheridancollege.beans.Protein;
import ca.sheridancollege.beans.Recipe;
import ca.sheridancollege.beans.RecipeDescription;
import ca.sheridancollege.beans.RecipeIngredient;
import ca.sheridancollege.beans.RecipeMarker;
import ca.sheridancollege.beans.RecipesAuthenticated;
import ca.sheridancollege.email.Email;
import ca.sheridancollege.repositories.AuthUserContinentRepository;
import ca.sheridancollege.repositories.CountryRepository;
import ca.sheridancollege.repositories.EndUserRepository;
import ca.sheridancollege.repositories.IngredientRepository;
import ca.sheridancollege.repositories.InstructionRepository;
import ca.sheridancollege.repositories.MeasurementRepository;
import ca.sheridancollege.repositories.MessageRepository;
import ca.sheridancollege.repositories.NutritionInformationRepository;
import ca.sheridancollege.repositories.ProteinRepository;
import ca.sheridancollege.repositories.RecipeIngredientRepository;
import ca.sheridancollege.repositories.RecipeRepository;
import ca.sheridancollege.repositories.RecipesAuthenticatedRepository;
import ca.sheridancollege.repositories.UserRepository;

/**
 * MamasDish_WebApplication
 * APIController.java
 * Purpose: Contains custom APIs that were made
 * 
 * @author Portia Ocran
 * @author Kowsiya Srikantharajah
 * @author Razan Alsaddi
 * @author Bilaal Rashid
 */
@RestController
@RequestMapping("/mamasdish")
public class APIController {
	
	// Repositories
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
	
	@Autowired
	private MessageRepository mssgRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private EndUserRepository endUserRepo;
	
	@Autowired
	private NutritionInformationRepository nutritionInformationRepo;
	
	@Autowired
	private AuthUserContinentRepository authContRepo;
	
	/**
	 * API that adds ingredients to the recipe
	 * 
	 * @param ingredient
	 * @param quantity
	 * @param measurement
	 * @param recipeId
	 * @param proteinId
	 * @return 1
	 */
	@GetMapping(value = "/addIngredient/{ingredient}/{quantity}/{measurement}/{recipeId}/{proteinId}")
	public int addIngredient(@PathVariable String ingredient, @PathVariable int quantity, @PathVariable int measurement,
			@PathVariable int recipeId, @PathVariable int proteinId) {
		
		// Getting the ingredient
		Ingredient ingred = ingredientRepo.findByIngredientName(ingredient);
		
		// Getting the recipe
		Recipe newRecipe = recipeRepo.findById(Long.valueOf(recipeId)).get();
		
		// Variable that will store the measurement
		Measurement recipeMeasurement = null;
		
		// Variable that will store the protein
		Protein ingProtein = null;
		
		// Check if the measurement is not empty
		if (measurement != 0) {
			// Get the ingredient's measurement
			recipeMeasurement = measurementRepo.findById(Long.valueOf(measurement)).get();
		}
		
		// Check if the measurement is not empty
		if (proteinId != 0) {
			// Get the ingredient's protein
			ingProtein = proteinRepo.findById(Long.valueOf(proteinId)).get();
		}
		
		if (ingred == null) {
			// Creating a new ingredient object
			ingred = new Ingredient();
			
			// Setting the name of the ingredient
			ingred.setIngredientName(ingredient);
			
			// Setting the protein of the ingredient
			ingred.setProtein(ingProtein);
			
			// Saving the ingredient to the IngredientRepository
			ingred = ingredientRepo.save(ingred);
		}
		
		RecipeIngredient recipeIngred = new RecipeIngredient().builder().ingredient(ingred).quantity(quantity)
				.measurement(recipeMeasurement).recipe(newRecipe).build();

		newRecipe.getIngredients().add(recipeIngred);
		
		recipeRepo.save(newRecipe);

		return 1;
	}
	
	/**
	 * API that will add all of the nutrition information
	 * 
	 * @param totalFat
	 * @param saturatedFat
	 * @param cholesterol
	 * @param sodium
	 * @param totalCarbohydrate
	 * @param dietaryFiber
	 * @param sugars
	 * @param protein
	 * @param calories
	 * @param recipeId
	 * @return 1
	 * @throws IOException
	 */
	@GetMapping(value = "/addNutritionInformation/{totalFat}/{saturatedFat}/{cholesterol}/{sodium}/{totalCarbohydrate}/{dietaryFiber}/{sugars}/{protein}/{calories}/{recipeId}")
    public int addNutritionInformation(@PathVariable int totalFat, @PathVariable int saturatedFat, @PathVariable int cholesterol,
            @PathVariable int sodium, @PathVariable int totalCarbohydrate, @PathVariable int dietaryFiber, @PathVariable int sugars,
            @PathVariable int protein, @PathVariable int calories, @PathVariable int recipeId) throws IOException {
		
		// Getting the recipe's ID
        Recipe recipe = recipeRepo.findById(Long.valueOf(recipeId)).get();
        
        NutritionInformation nutritionInformation = new NutritionInformation().builder().totalFat(totalFat).saturatedFat(saturatedFat)
                .cholesterol(cholesterol).sodium(sodium).totalCarbohydrate(totalCarbohydrate).dietaryFiber(dietaryFiber).sugars(sugars)
                .protein(protein).calories(calories).recipe(recipe).build();
        
        // Saving nutrition information
		nutritionInformationRepo.save(nutritionInformation);
		
        return 1;
    }
	
	// Add calorie info
	/**
	 * API that adds the calories information to the recipe
	 * 
	 * @param calories
	 * @param recipeId
	 * @return 1
	 */
	@GetMapping(value = "/addCalorie/{calories}/{recipeId}")
    public int addCalorie( @PathVariable String calories, @PathVariable int recipeId){
		
		// Getting the recipe's ID
        Recipe recipe = recipeRepo.findById(Long.valueOf(recipeId)).get();
        
        // Variable that parses the calories value
        float caloriesFlt = Float.parseFloat(calories);
        
        // Setting the calories in the recipe
		recipe.setCalories(caloriesFlt);
		
		// Saving the recipe
		recipeRepo.save(recipe);
		
        return 1;
    }
	
	/**
	 * API that deletes an ingredient from the recipe
	 * 
	 * @param recipeId
	 * @return deletedRecords
	 */
	@Transactional
	@GetMapping(value = "/deleteIngredients/{recipeId}")
	public long deleteIngredient(@PathVariable long recipeId) {
		
		// Getting the recipe's ID
		Recipe recipe = recipeRepo.findById(recipeId).get();
		
		recipe.getIngredients().clear();
		
		recipeRepo.save(recipe);
		
		long deletedRecords = recipeIngredientRepo.deleteByRecipeId(recipeId);
		
		return deletedRecords;
	}
	
	/**
	 * API that deletes an instruction from the recipe
	 * 
	 * @param recipeId
	 * @return 1
	 */
	@Transactional
	@GetMapping(value="/deleteInstructions/{recipeId}")
	public long deleteInstruction(@PathVariable long recipeId) {
		
		// Getting the recipe's ID
		Recipe recipe = recipeRepo.findById(recipeId).get();
		
		recipe.getInstructions().clear();
		
		recipeRepo.save(recipe);
		
		return 1;
	}
	
	/**
	 * API that adds instructions to the recipe.
	 * 
	 * @param instruction
	 * @param recipeId
	 * @return 1
	 */
	@PostMapping(value = "/addInstructions/{recipeId}", headers = { "Content-type=application/json" })
	public int addInstruction(@RequestBody Instruction instruction, @PathVariable int recipeId) {
		
		// Saving the instruction to the InstructionRepository
		Instruction saveInstruction = instructionRepo.save(instruction);
		
		// Getting the recipe's ID
		Recipe recipe = recipeRepo.findById(Long.valueOf(recipeId)).get();
		
		// Adding the instruction to the recipe
		recipe.getInstructions().add(saveInstruction);
		
		// Saving the to the recipe's repo
		recipeRepo.save(recipe);
		
		return 1;
	}
	
	/**
	 * List that has all of the instructions
	 * 
	 * @return A list of instructions
	 */
	@GetMapping("/instructions")
	public List<Instruction> listInstructions() {
		return instructionRepo.findAll();
	}
	
	/**
	 * List that has all the countries
	 * 
	 * @return A list of all the countries
	 */
	@GetMapping("/countries")
	public List<Country> getCoordinates() {
		return countryRepo.findAll();
	}
	
	/**
	 * API that approves the recipe
	 * 
	 * @param model
	 * @param id
	 * @return 1
	 */
	@GetMapping(value = "/admin/approveRecipe/{id}")
	public int approveRecipe(Model model, @PathVariable int id) {
		
		// Getting the recipe's ID
		Recipe recipe = recipeRepo.findById(Long.valueOf(id)).get();

		recipe.setAuth(true);
		
		recipeRepo.save(recipe);

		return 1;
	}
	
	/**
	 * API that sends an email to the admin saying that there is a new recipe that needs approval.
	 * 
	 * @param model
	 * @param id
	 * @return 1
	 */
	@GetMapping(value = "/admin/approvalRequest/{id}")
	public int sendAuthEmail(Model model, @PathVariable int id) {
		
		// Getting the recipe's ID
		Recipe recipe = recipeRepo.findById(Long.valueOf(id)).get();
		
		List <Integer> authCont = authContRepo.getAuthUser((int) (long) recipe.getCountry().getContinent().getId());
		
		for(Integer authUser : authCont) {
			
			// Creating a nessageSystem object
			MessageSystem mssg = new MessageSystem();
			
			// Getting the user
			EndUser user = endUserRepo.findById(Long.valueOf(authUser)).get();
			
			// Variable that contains the admin's email
			String userEmail = user.getEmail();
			
			// Variable that contains the subject of the email
			String subject = "Recipe Requiring Authentication";
			
			// Variable that contains the body of the email
			String body = "Recipe from " + recipe.getCountry().getContinent().getName() + " requires authentication";
			
			// Setting the message
			mssg.setSubject(subject);
			mssg.setSender(user.getFirstName() + " " + user.getLastName());
			mssg.setDateSent(LocalDateTime.now());
			mssg.setReceiver("Mama's Dish Authenticators");
			mssg.setNew(true);
			mssg.setMessage(body);
			mssg.setRecipeId(recipe.getId());
			
			// Saving the message
			mssgRepo.save(mssg);
			
			user.getMessages().add(mssg);
			
			endUserRepo.save(user);
			
			// Sending the email
			email.sendEmail(userEmail, subject, body);
		}
		
		MessageSystem mssg = new MessageSystem();
		
		EndUser chef = recipe.getChef().getEnduser();
		
		// Variable that contains the chef's email
		String chefEmail = chef.getEmail();
		
		// Variable that contains the subject of the email
		String subject = "Thank You from Mamas Dish";
		
		// Variable that contains the body of the email
		String body = "Thank you for adding your authentic recipe to Mamas Dish.";
		body += "Please allow 24-48 hrs for approval from our authentication team.";
		
		// Sending the email
		email.sendEmail(chefEmail, subject, body);
		
		// Setting the message
		mssg.setSubject("Approval Needed for new Recipe");
		mssg.setSender(chef.getFirstName() + " " + chef.getLastName());
		mssg.setDateSent(LocalDateTime.now());
		mssg.setReceiver("Mama's Dish Admin");
		mssg.setNew(true);
		mssg.setMessage("New recipe available for review");
		mssg.setRecipeId(recipe.getId());
		
		// Saving the message
		mssgRepo.save(mssg);

		return 1;
	}
	
	/**
	 * API that sends an email to the chef about their recipe.
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/admin/RecipeApproval/{id}")
	public int sendApprovalEmail(Model model, @PathVariable int id) {
		
		// Getting the recipe's ID
		Recipe recipe = recipeRepo.findById(Long.valueOf(id)).get();
		
		// Variable that contains the recipe's title
		String recipeTitle = recipe.getTitle();
		
		// Variable that contains the chef's email
		String chefEmail = recipe.getChef().getEnduser().getEmail();
		
		// Variable that contains the subject of the email
		String subject = recipeTitle + " has been Approved!";
		
		// Variable that contains the body of the email
		String body = "Your recipe has now been approved!";

		// Sending the email
		email.sendEmail(chefEmail, subject, body);
		
		MessageSystem mssg = new MessageSystem();
		
		// Setting the message
		mssg.setSubject(recipeTitle + " has been approved.");
		mssg.setSender("Mamas Dish Admin");
		mssg.setDateSent(LocalDateTime.now());
		mssg.setReceiver(recipe.getChef().getEnduser().getFirstName() + " " + recipe.getChef().getEnduser().getLastName());
		mssg.setNew(true);
		mssg.setMessage(body);
		mssg.setRecipeId(recipe.getId());
		
		// Saving the message
		mssgRepo.save(mssg);
		
		EndUser endUser = recipe.getChef().getEnduser();
		
		endUser.getMessages().add(mssg);
		
		endUserRepo.save(endUser);

		return 1;
	}
	
	/**
	 * API that creates a marker on the map for the recipe
	 * 
	 * @return Recipe marker for the map
	 */
	@GetMapping("/countryRecipes")
	public List<RecipeMarker> countryRecipes() {
		
		// List that contains all of the countries 
		List<Country> countries = countryRepo.findAll();
		
		// List that contains all of the recipe markers
		List<RecipeMarker> recipeMarkers = new ArrayList<RecipeMarker>();
		
		// Looping through the countries
		for (Country c : countries) {
			// Creating a new recipe marker object
			RecipeMarker marker = new RecipeMarker();

			marker.setCountry(c);
			
			List<Recipe> recipes = recipeRepo.findByCountry_nameContainingIgnoreCase(c.getName());
			
			Collections.shuffle(recipes);
			
			// Looping through the recipes
			for (Recipe r : recipes) {
				// Creating a new description object
				RecipeDescription description = new RecipeDescription();
				
				// Setting up the description
				description.setRecipeId(r.getId());
				description.setRecipeTitle(r.getTitle());
				description.setRecipeImg(r.getRecipeImg());
				
				// Adding the description to the marker
				marker.getRecipes().add(description);
			}
			recipeMarkers.add(marker);
		}
		return recipeMarkers;
	}
	
	/**
	 * API that gets the email count.
	 * 
	 * @param id
	 * @param auth
	 * @return Email count
	 */
	@GetMapping("/checkEmail/{id}")
	public int checkEmail(@PathVariable int id, Authentication auth) {
		
		MessageSystem mssg = mssgRepo.findById(Long.valueOf(id)).get();
		
		mssg.setNew(false);
		
		EndUser user = endUserRepo.findByEmail(auth.getName());
		
		mssgRepo.save(mssg);
		
		if(mssg.getReceiver().equals("Mama's Dish Admin")) {
			return mssgRepo.getAdminEmailCount();
		} else {
			return mssgRepo.emailCount(Long.valueOf(user.getId()));
		}
	}
	
	/**
	 * API that deletes an email.
	 * 
	 * @param id
	 * @param auth
	 * @return the size of deleted emails
	 */
	@GetMapping("/deleteEmail/{id}")
	public int deleteEmail(@PathVariable int id, Authentication auth) {
		
		MessageSystem mssg = mssgRepo.findById(Long.valueOf(id)).get();
		
		EndUser user = endUserRepo.findByEmail(auth.getName());

		mssg.setDeleted(true);
		
		mssgRepo.save(mssg);
		
		if(mssg.getReceiver().equals("Mama's Dish Admin")) {
			return mssgRepo.getAdminDeletedEmails().size();
		} else {
			return mssgRepo.getDeletedEmails(Long.valueOf(user.getId())).size();
		}
	}
}
