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

@Controller
public class UserController {
	
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
	
	
	//method to view the profile
	@GetMapping("/viewProfile")
	public String goViewProfile(Model model, Authentication auth) {

		//get user
		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());
		
		model.addAttribute("emails", emailCount);
		model.addAttribute("user", user);		
	
		//get user preferences
		String proteins = "";
		for(Protein p : user.getProtein()) {
			proteins += p.getProteinType() + ", ";
		}
		
		if(proteins != "") {
			proteins = proteins.substring(0, proteins.length() - 2);
		}
		
		String cuisines = "";
		for (Cuisine c : user.getCuisine()) {
			cuisines += c.getCuisineName() + ", ";
		}
		if(cuisines != "") {
			cuisines = cuisines.substring(0, cuisines.length() - 2);
		}
		
		String countries = "";
		for(Country c : user.getCountry()) {
			countries += c.getName() + ", "; 
		}
		if(countries != "") {
			countries = countries.substring(0, countries.length() - 2);
		}
		
		String diets = "";
		for(Diet d : user.getDiet()) {
			diets += d.getDietType() + ", ";
		}
		if(diets != "") {
			diets = diets.substring(0, diets.length() - 2);
		}
	
		model.addAttribute("countries", countries);
		model.addAttribute("proteins", proteins);
		model.addAttribute("cuisines", cuisines);
		model.addAttribute("diets", diets);
		
		//get chef info
		Chef chef = chefRepo.findByEnduser_Email(user.getEmail());
		if(chef != null) {
			model.addAttribute("chef", chef);
		}
			
		return "users/viewProfile.html";
	}
	
	//Method to save changes
	@PostMapping("/viewProfile")
	public String saveProfileChanges(@RequestParam long id, @RequestParam String firstName, @RequestParam String lastName,
			@RequestParam(required = false, value = "countries[]") String[] countries, @RequestParam(required = false, value = "proteins[]") String[] proteins,
			@RequestParam(required = false, value = "diets[]") String[] diets, @RequestParam(required = false, value = "cuisines[]") String[] cuisines,
			@RequestParam (required = false) String description, Model model, Authentication auth) {
		
		EndUser user = endUserRepo.findById(id).get();
		EndUser userEmail = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(userEmail.getId());
		model.addAttribute("emails", emailCount);
		
		//save changes for user
		user.setFirstName(firstName);
		user.setLastName(lastName);
		
		//save changes to preferences
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
		endUserRepo.save(user);
				
		//if user is a chef
		Chef chef = chefRepo.findByEnduser_Email(user.getEmail());
		if(chef != null) {
			chef.setDescription(description);
			chefRepo.save(chef);
			model.addAttribute("chef", chef);
		}		
		
		return "redirect:/viewProfile";	
	}
	
	//method to go to the edit profile page
	@PostMapping("/editProfile")
	public String goEditProfile(Model model, Authentication auth, @RequestParam long id){
		EndUser user = endUserRepo.findById(id).get();
		model.addAttribute("user", user);
		
		EndUser userEmail = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(userEmail.getId());
		model.addAttribute("emails", emailCount);

		model.addAttribute("countries", countryRepo.findByOrderByName());
		model.addAttribute("cuisines", cuisineRepo.findByOrderByCuisineName());
		model.addAttribute("diets", dietRepo.findAll());
		model.addAttribute("proteins", proteinRepo.findAll());
		
		Chef chef = chefRepo.findByEnduser_Email(user.getEmail());
		if(chef != null) {
			model.addAttribute("chef", chef);
		}
		
		return "users/editProfile.html";
	}

	//Method to display emails in inbox
	@GetMapping("/messages/inbox")
	public String Messages(Model model, Authentication auth) {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());
		boolean isAdmin = false;
		List<Role> roles = userRepo.findByUsername(auth.getName()).getRoles();
		
		for(Role role : roles) {
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
			model.addAttribute("messages", mssg);
			model.addAttribute("emails", mssgRepo.getAdminEmailCount());
			model.addAttribute("deleted", mssgRepo.getAdminDeletedEmails().size() + mssgRepo.getDeletedEmails(user.getId()).size());
 
		} else {
			model.addAttribute("messages", mssgRepo.getEmails(user.getId()));
			model.addAttribute("emails", emailCount);
			model.addAttribute("deleted", mssgRepo.getDeletedEmails(user.getId()).size());
		}
		
		model.addAttribute("user", user);

		return "users/inbox.html";
	}
	
	@GetMapping("/messages/deleted")
	public String deletedMessages(Model model, Authentication auth) {
		EndUser user = endUserRepo.findByEmail(auth.getName());
		int emailCount = mssgRepo.emailCount(user.getId());
		List<MessageSystem> mssgs = mssgRepo.getDeletedEmails(user.getId());
		List<Role> roles = userRepo.findByUsername(auth.getName()).getRoles();
		boolean isAdmin = false;

		for(Role role : roles) {
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
			model.addAttribute("messages", mssg);
			model.addAttribute("emails", mssgRepo.getAdminEmailCount());
			model.addAttribute("deleted", mssgRepo.getAdminDeletedEmails().size() +  mssgRepo.getDeletedEmails(user.getId()).size());

		} else {
			model.addAttribute("messages", mssgRepo.getDeletedEmails(user.getId()));
			model.addAttribute("emails", emailCount);
			model.addAttribute("deleted", mssgRepo.getDeletedEmails(user.getId()).size());
		}
		
		model.addAttribute("user", user);
		
		return "users/inbox.html";
	}
	
}

