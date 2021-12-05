package ca.sheridancollege.controllers;

import java.util.Collections;
import java.util.List;
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
import ca.sheridancollege.beans.MessageSystem;
import ca.sheridancollege.beans.Protein;
import ca.sheridancollege.beans.Role;
import ca.sheridancollege.repositories.ChefRepository;
import ca.sheridancollege.repositories.CountryRepository;
import ca.sheridancollege.repositories.CuisineRepository;
import ca.sheridancollege.repositories.DietRepository;
import ca.sheridancollege.repositories.EndUserRepository;
import ca.sheridancollege.repositories.MessageRepository;
import ca.sheridancollege.repositories.ProteinRepository;
import ca.sheridancollege.repositories.UserRepository;

/**
 * MamasDish_WebApplication
 * UserController.java
 * Purpose: Contains all of the user functionality
 * 
 * @author Portia Ocran
 * @author Kowsiya Srikantharajah
 * @author Razan Alsaddi
 * @author Bilaal Rashid
 */
@Controller
public class UserController {
	
	// Repositories
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private EndUserRepository endUserRepo;
	
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
	
	@Autowired
	private MessageRepository mssgRepo;
	
	
	// Method to view the profile
	/**
	 * Going to the view profile page for the user and grabbing all of the user's information as needed.
	 * 
	 * @param model
	 * @param auth
	 * @return viewProfile.html page
	 */
	@GetMapping("/viewProfile")
	public String goViewProfile(Model model, Authentication auth) {

		// Getting user's name
		EndUser user = endUserRepo.findByEmail(auth.getName());
		
		// Variable emailCount is used to keep track of the email count
		int emailCount = mssgRepo.emailCount(user.getId());

		// Storing user's preferences into variables
		String proteins = "";
		String cuisines = "";
		String countries = "";
		String diets = "";
		
		/**
		 * Looping through the user's protein, cuisine, country, and diet preferences and storing them into variables.
		 * 
		 * Checking if the user's preference is not empty.
		 */
		for(Protein p : user.getProtein()) {
			proteins += p.getProteinType() + ", ";
		}
		
		if(proteins != "") {
			proteins = proteins.substring(0, proteins.length() - 2);
		}
		
		for (Cuisine c : user.getCuisine()) {
			cuisines += c.getCuisineName() + ", ";
		}
		
		if(cuisines != "") {
			cuisines = cuisines.substring(0, cuisines.length() - 2);
		}
	
		for(Country c : user.getCountry()) {
			countries += c.getName() + ", "; 
		}
		
		if(countries != "") {
			countries = countries.substring(0, countries.length() - 2);
		}

		for(Diet d : user.getDiet()) {
			diets += d.getDietType() + ", ";
		}
		
		if(diets != "") {
			diets = diets.substring(0, diets.length() - 2);
		}
		
		// Creating attributes so that we can use them later in HTML
		model.addAttribute("emails", emailCount);
		model.addAttribute("user", user);
		model.addAttribute("countries", countries);
		model.addAttribute("proteins", proteins);
		model.addAttribute("cuisines", cuisines);
		model.addAttribute("diets", diets);
		
		
		// Checking if the user is a chef
		Chef chef = chefRepo.findByEnduser_Email(user.getEmail());
		if(chef != null) {
			model.addAttribute("chef", chef);
		}
			
		return "users/viewProfile.html";
	}
	
	/**
	 * Saving any changes that was made when he user is editing their profile.
	 * 
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param countries
	 * @param proteins
	 * @param diets
	 * @param cuisines
	 * @param description
	 * @param model
	 * @param auth
	 * @return we redirect to viewProfile.html page
	 */
	@PostMapping("/viewProfile")
	public String saveProfileChanges(@RequestParam long id, @RequestParam String firstName, @RequestParam String lastName,
			@RequestParam(required = false, value = "countries[]") String[] countries, @RequestParam(required = false, value = "proteins[]") String[] proteins,
			@RequestParam(required = false, value = "diets[]") String[] diets, @RequestParam(required = false, value = "cuisines[]") String[] cuisines,
			@RequestParam (required = false) String description, Model model, Authentication auth) {
		
		// Getting user's ID
		EndUser user = endUserRepo.findById(id).get();
		
		// Getting user's name
		EndUser userEmail = endUserRepo.findByEmail(auth.getName());
		
		// Variable emailCount is used to keep track of the email count
		int emailCount = mssgRepo.emailCount(userEmail.getId());
		
		// Creating attributes so that we can use them later in HTML
		model.addAttribute("emails", emailCount);
		
		// Save changes for user
		user.setFirstName(firstName);
		user.setLastName(lastName);
		
		// Save changes to user's preferences
		if(proteins != null) {
			user.getProtein().clear();
			if (proteins.length != 0) {
				for (String p : proteins) {
					user.getProtein().add(proteinRepo.findByProteinType(p));
				}
			}
		}
		
		if(cuisines != null) {
			user.getCuisine().clear();
			if (cuisines.length != 0) {
				for (String cuisine : cuisines) {
					user.getCuisine().add(cuisineRepo.findByCuisineName(cuisine));
				}
			}
		}
			
		if(countries != null) {
			user.getCountry().clear();
			if (countries.length != 0) {
				for (String country : countries) {
					user.getCountry().add(countryRepo.findByName(country));
				}
			}
		}
		
		if(diets != null) {
			user.getDiet().clear();
			if (diets.length != 0) {
				for (String diet : diets) {
					user.getDiet().add(dietRepo.findByDietType(diet));
				}
			}
		}
		
		// Updating the EndUserRepository with new changes that the user has made
		endUserRepo.save(user);
				
		// Checking if the user is a chef
		Chef chef = chefRepo.findByEnduser_Email(user.getEmail());
		if(chef != null) {
			chef.setDescription(description);
			chefRepo.save(chef);
			model.addAttribute("chef", chef);
		}		
		
		return "redirect:/viewProfile";	
	}

	/**
	 * Going to the edit profile page for the user and grabbing all of their information.
	 * 
	 * @param model
	 * @param auth
	 * @param id
	 * @return editProfile.html page
	 */
	@PostMapping("/editProfile")
	public String goEditProfile(Model model, Authentication auth, @RequestParam long id){
		
		// Getting user's ID
		EndUser user = endUserRepo.findById(id).get();
		
		// Getting user's name
		EndUser userEmail = endUserRepo.findByEmail(auth.getName());
		
		// Variable emailCount is used to keep track of the email count
		int emailCount = mssgRepo.emailCount(userEmail.getId());
		
		// Creating attributes so that we can use them later in HTML
		model.addAttribute("emails", emailCount);
		model.addAttribute("user", user);
		model.addAttribute("countries", countryRepo.findByOrderByName());
		model.addAttribute("cuisines", cuisineRepo.findByOrderByCuisineName());
		model.addAttribute("diets", dietRepo.findAll());
		model.addAttribute("proteins", proteinRepo.findAll());
		
		// Checking if the user is a chef
		Chef chef = chefRepo.findByEnduser_Email(user.getEmail());
		if(chef != null) {
			model.addAttribute("chef", chef);
		}
		
		return "users/editProfile.html";
	}

	/**
	 * Going to the inbox page and displaying all emails.
	 * 
	 * @param model
	 * @param auth
	 * @return inbox.html page
	 */
	@GetMapping("/messages/inbox")
	public String Messages(Model model, Authentication auth) {
		
		// Getting user's name
		EndUser user = endUserRepo.findByEmail(auth.getName());
		
		// Variable emailCount is used to keep track of the email count
		int emailCount = mssgRepo.emailCount(user.getId());
		
		// Boolean variable that will be used to check if the user is an admin
		boolean isAdmin = false;
		
		// Getting the user's role
		List<Role> roles = userRepo.findByUsername(auth.getName()).getRoles();
		
		// Looping through all of the roles
		for(Role role : roles) {
			// Check if the user has the admin role
			if(role.getRolename().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}

		if(isAdmin) {
			List<MessageSystem> mssg = mssgRepo.getAdminEmailList();
			
			mssg.addAll(mssgRepo.getEmails(user.getId()));
			
			Collections.sort(mssg, (o1, o2) -> o1.getDateSent().compareTo(o2.getDateSent()));
			Collections.reverse(mssg);
			
			// Creating attributes so that we can use them later in HTML
			model.addAttribute("messages", mssg);
			model.addAttribute("emails", mssgRepo.getAdminEmailCount());
			model.addAttribute("deleted", mssgRepo.getAdminDeletedEmails().size() + mssgRepo.getDeletedEmails(user.getId()).size());
 
		} else {
			// Creating attributes so that we can use them later in HTML
			model.addAttribute("messages", mssgRepo.getEmails(user.getId()));
			model.addAttribute("emails", emailCount);
			model.addAttribute("deleted", mssgRepo.getDeletedEmails(user.getId()).size());
		}
		
		// Creating a usesr attribute so that we can use it later in HTML
		model.addAttribute("user", user);

		return "users/inbox.html";
	}
	
	/**
	 * Going to the deleted message page to view all of the messages that have been deleted by the user.
	 * 
	 * @param model
	 * @param auth
	 * @return inbox.html page
	 */
	@GetMapping("/messages/deleted")
	public String deletedMessages(Model model, Authentication auth) {
		
		// Getting user's name
		EndUser user = endUserRepo.findByEmail(auth.getName());
		
		// Variable emailCount is used to keep track of the email count
		int emailCount = mssgRepo.emailCount(user.getId());
		
		// Getting the user's deleted messages
		List<MessageSystem> mssgs = mssgRepo.getDeletedEmails(user.getId());
		
		// Getting the user's role
		List<Role> roles = userRepo.findByUsername(auth.getName()).getRoles();
		
		// Boolean variable that will be used to check if the user is an admin
		boolean isAdmin = false;
		
		// Looping through all of the roles
		for(Role role : roles) {
			// Check if the user has the admin role
			if(role.getRolename().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		
		if(isAdmin) {
			List<MessageSystem> mssg = mssgRepo.getAdminDeletedEmails();
			
			mssg.addAll(mssgRepo.getDeletedEmails(user.getId()));
			
			Collections.sort(mssg, (o1, o2) -> o1.getDateSent().compareTo(o2.getDateSent()));
			Collections.reverse(mssg);
			
			// Creating attributes so that we can use them later in HTML
			model.addAttribute("messages", mssg);
			model.addAttribute("emails", mssgRepo.getAdminEmailCount());
			model.addAttribute("deleted", mssgRepo.getAdminDeletedEmails().size() +  mssgRepo.getDeletedEmails(user.getId()).size());

		} else {
			// Creating attributes so that we can use them later in HTML
			model.addAttribute("messages", mssgRepo.getDeletedEmails(user.getId()));
			model.addAttribute("emails", emailCount);
			model.addAttribute("deleted", mssgRepo.getDeletedEmails(user.getId()).size());
		}
		
		// Creating a usesr attribute so that we can use it later in HTML
		model.addAttribute("user", user);
		
		return "users/inbox.html";
	}
}

