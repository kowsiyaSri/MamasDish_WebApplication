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
import ca.sheridancollege.email.Email;
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
import ca.sheridancollege.repositories.UserRepository;

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
	
	@Autowired
	private MessageRepository mssgRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private EndUserRepository endUserRepo;
	
	@Autowired
	private NutritionInformationRepository nutritionInformationRepo;

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

	@GetMapping(value = "/addNutritionInformation/{totalFat}/{saturatedFat}/{cholesterol}/{sodium}/{totalCarbohydrate}/{dietaryFiber}/{sugars}/{protein}/{calories}/{recipeId}")
    public int addNutritionInformation(@PathVariable int totalFat, @PathVariable int saturatedFat, @PathVariable int cholesterol,
            @PathVariable int sodium, @PathVariable int totalCarbohydrate, @PathVariable int dietaryFiber, @PathVariable int sugars,
            @PathVariable int protein, @PathVariable int calories, @PathVariable int recipeId) throws IOException {
        Recipe recipe = recipeRepo.findById(Long.valueOf(recipeId)).get();
        NutritionInformation nutritionInformation = new NutritionInformation().builder().totalFat(totalFat).saturatedFat(saturatedFat)
                .cholesterol(cholesterol).sodium(sodium).totalCarbohydrate(totalCarbohydrate).dietaryFiber(dietaryFiber).sugars(sugars)
                .protein(protein).calories(calories).recipe(recipe).build();
		nutritionInformationRepo.save(nutritionInformation);
        return 1;
    }

	@Transactional
	@GetMapping(value = "/deleteIngredients/{recipeId}")
	public long deleteIngredient(@PathVariable long recipeId) {
		Recipe recipe = recipeRepo.findById(recipeId).get();
		recipe.getIngredients().clear();
		recipeRepo.save(recipe);
		
		long deletedRecords = recipeIngredientRepo.deleteByRecipeId(recipeId);
		return deletedRecords;
	}
	
	@Transactional
	@GetMapping(value="/deleteInstructions/{recipeId}")
	public long deleteInstruction(@PathVariable long recipeId) {
		Recipe recipe = recipeRepo.findById(recipeId).get();
		recipe.getInstructions().clear();
		recipeRepo.save(recipe);
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
		MessageSystem mssg = new MessageSystem();
		EndUser chef = recipe.getChef().getEnduser();
		String chefEmail = chef.getEmail();
		String subject = "Thank You from Mamas Dish";
		String body = "Thank you for adding your authentic recipe to Mamas Dish.";
		body += "Please allow 24-48 hrs for approval from our authentication team.";

		email.sendEmail(chefEmail, subject, body);
		mssg.setSubject("Approval Needed for new Recipe");
		mssg.setSender(chef.getFirstName() + " " + chef.getLastName());
		mssg.setDateSent(LocalDateTime.now());
		mssg.setReceiver("Mama's Dish Admin");
		mssg.setNew(true);
		mssg.setMessage("New recipe available for review");
		mssg.setRecipeId(recipe.getId());
		mssgRepo.save(mssg);


		return 1;
	}

	@GetMapping(value = "/admin/RecipeApproval/{id}")
	public int sendApprovalEmail(Model model, @PathVariable int id) {

		Recipe recipe = recipeRepo.findById(Long.valueOf(id)).get();
		String recipeTitle = recipe.getTitle();
		String chefEmail = recipe.getChef().getEnduser().getEmail();
		String subject = recipeTitle + " has been Approved!";
		String body = "Your recipe has now been approved!";

		email.sendEmail(chefEmail, subject, body);
		
		MessageSystem mssg = new MessageSystem();
		mssg.setSubject(recipeTitle + " has been approved.");
		mssg.setSender("Mamas Dish Admin");
		mssg.setDateSent(LocalDateTime.now());
		mssg.setReceiver(recipe.getChef().getEnduser().getFirstName() + " " + recipe.getChef().getEnduser().getLastName());
		mssg.setNew(true);
		mssg.setMessage(body);
		mssg.setRecipeId(recipe.getId());
		
		mssgRepo.save(mssg);
		
		EndUser endUser = recipe.getChef().getEnduser();
		endUser.getMessages().add(mssg);
		endUserRepo.save(endUser);

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
