package ca.sheridancollege.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.Chef;
import ca.sheridancollege.beans.Country;
import ca.sheridancollege.beans.Cuisine;
import ca.sheridancollege.beans.Diet;
import ca.sheridancollege.beans.EndUser;
import ca.sheridancollege.beans.Protein;
import ca.sheridancollege.beans.UserPreference;
import ca.sheridancollege.repositories.ChefRepository;
import ca.sheridancollege.repositories.CountryRepository;
import ca.sheridancollege.repositories.CuisineRepository;
import ca.sheridancollege.repositories.DietRepository;
import ca.sheridancollege.repositories.EndUserRepository;
import ca.sheridancollege.repositories.ProteinRepository;
import ca.sheridancollege.repositories.UserPreferenceRepository;
import ca.sheridancollege.repositories.UserRepository;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private EndUserRepository endUserRepo;
	
	@Autowired
	private UserPreferenceRepository userPrefRepo;
	
	@Autowired
	private CountryRepository countryRepo;

	@Autowired
	private CuisineRepository cuisineRepo;

	@Autowired
	private DietRepository dietRepo;

	@Autowired
	private ProteinRepository proteinRepo;
	
	@Autowired
	private ChefRepository chefRepo;
	
	
	//method to view the profile
	@GetMapping("/viewProfile")
	public String goViewProfile(Model model, Authentication auth) {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPref", userPrefRepo.findByEnduser_id(user.getId()));
		
		Chef chef = chefRepo.findByEnduser_Email(user.getEmail());
		if(chef != null) {
			model.addAttribute("chef", chef);
		}
		
		return "/users/viewProfile.html";
	}
	
	//Method to save changes
	@PostMapping("/viewProfile")
	public String saveProfileChanges(@RequestParam long id, @RequestParam String firstName, @RequestParam String lastName,
			@RequestParam(required = false, defaultValue ="") String countryPref, @RequestParam(required = false, defaultValue ="") String cuisinePref, 
			@RequestParam(required = false, defaultValue ="") String dietPref, @RequestParam(required = false, defaultValue ="") String proteinPref,
			@RequestParam String description, Model model) {
		
		EndUser user = endUserRepo.findById(id).get();
		
		//save changes for user
		user.setFirstName(firstName);
		user.setLastName(lastName);
		endUserRepo.save(user);
		
		//save changes for userPreferece
		UserPreference userPref = userPrefRepo.findByEnduser_id(id);
		
		Country country = null;
		if (countryPref != ""){
			country = countryRepo.findByName(countryPref);
		}
		userPref.setCountry(country);
		
		Cuisine cuisine = null;
		if(cuisinePref != ""){
			cuisine = cuisineRepo.findByCuisineName(cuisinePref);
		}
		userPref.setCuisine(cuisine);
		
		Diet diet = null;
		if(dietPref != "") {
			diet = dietRepo.findByDietType(dietPref);
		}
		userPref.setDiet(diet);
		
		Protein protein = null;
		if(proteinPref != "") {
			protein = proteinRepo.findByProteinType(proteinPref);
		}
		userPref.setProtein(protein);
		
		userPrefRepo.save(userPref);
		
		//if user is a chef
		Chef chef = chefRepo.findByEnduser_Email(user.getEmail());
		if(chef != null) {
			chef.setDescription(description);
			chefRepo.save(chef);
			model.addAttribute("chef", chef);
		}
		
		//go back to view profile
		model.addAttribute("user", user);
		model.addAttribute("userPref", userPrefRepo.findByEnduser_id(user.getId()));
		
		return "/users/viewProfile.html";	
	}
	
	//method to go to the edit profile page
	@PostMapping("/editProfile")
	public String goEditProfile(Model model, @RequestParam long id){
		EndUser user = endUserRepo.findById(id).get();
		model.addAttribute("user", user);
		model.addAttribute("userPref", userPrefRepo.findByEnduser_id(id));
		model.addAttribute("countries", countryRepo.findByOrderByName());
		model.addAttribute("cuisines", cuisineRepo.findByOrderByCuisineName());
		model.addAttribute("diets", dietRepo.findAll());
		model.addAttribute("proteins", proteinRepo.findAll());
		
		Chef chef = chefRepo.findByEnduser_Email(user.getEmail());
		if(chef != null) {
			model.addAttribute("chef", chef);
		}
		
		return "/users/editProfile.html";
	}
}
