package ca.sheridancollege.controllers;

import java.io.Console;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import ca.sheridancollege.beans.Recipe;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.repositories.ChefRepository;
import ca.sheridancollege.repositories.CountryRepository;
import ca.sheridancollege.repositories.CuisineRepository;
import ca.sheridancollege.repositories.DietRepository;
import ca.sheridancollege.repositories.EndUserRepository;
import ca.sheridancollege.repositories.ProteinRepository;
import ca.sheridancollege.repositories.RoleRepository;
import ca.sheridancollege.repositories.UserRepository;

@Controller
public class RegisterController {
	
	@Autowired
	private EndUserRepository endUserRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private ChefRepository chefRepo;
	
	@Autowired
	private CountryRepository countryRepo;

	@Autowired
	private CuisineRepository cuisineRepo;

	@Autowired
	private DietRepository dietRepo;

	@Autowired
	private ProteinRepository proteinRepo;

	// method to register user
	@PostMapping("/register")
	public String processRegister(@RequestParam String email, @RequestParam String fname, @RequestParam String lname,
			@RequestParam String password, @RequestParam String password2,
			@RequestParam(required = false, defaultValue ="") String countryPref, @RequestParam(required = false, defaultValue ="") String cuisinePref, 
			@RequestParam(required = false, defaultValue ="") String dietPref, @RequestParam(required = false, defaultValue ="") String proteinPref, 
			@RequestParam(required = false) boolean isChef, @RequestParam(required = false) String description,
			Model model) {

		//checks if passwords match
		if (!password.equals(password2)) {

			model.addAttribute("errMssg", "Passwords MUST match.");
			model.addAttribute("emailInput", email);
			model.addAttribute("fNameInput", fname);
			model.addAttribute("lNameInput", lname);

			return "register.html";

		} //checks if email is already registered
		else if(userRepo.findByUsername(email) != null){
			
			model.addAttribute("errMssg", "Email already registered.");
			model.addAttribute("emailInput", email);
			model.addAttribute("fNameInput", fname);
			model.addAttribute("lNameInput", lname);


			return "register.html";
		} else {
			
			//creates and saves a new user
			EndUser endUser = EndUser.builder().firstName(fname).lastName(lname).email(email).password(password)
					.build();
			User user = new User(email, encodePassword(password));
			EndUser newUser = endUserRepo.save(endUser);
			
			//Retrieves users preferences
			Country country = null;
			if (countryPref != ""){
				country = countryRepo.findByName(countryPref);
			}
			
			Cuisine cuisine = null;
			if(cuisinePref != ""){
				cuisine = cuisineRepo.findByCuisineName(cuisinePref);
			}
			
			Diet diet = null;
			if(dietPref != "") {
				diet = dietRepo.findByDietType(dietPref);
			}
			
			Protein protein = null;
			if(proteinPref != "") {
				protein = proteinRepo.findByProteinType(proteinPref);
			}
			
			//save users preferences
			/*UserPreference userPref = UserPreference.builder().enduser(endUser).
					country(country).cuisine(cuisine).diet(diet).protein(protein).build();
			userPrefRepo.save(userPref);*/
			
			//if chef is selected will create a chef 
			if (isChef) {
				Chef chef = Chef.builder().description(description).recipes(new ArrayList<Recipe>()).enduser(endUser)
						.build();
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
	public String Register(Model model) {
		
		model.addAttribute("countries", countryRepo.findByOrderByName());
		model.addAttribute("cuisines", cuisineRepo.findByOrderByCuisineName());
		model.addAttribute("diets", dietRepo.findAll());
		model.addAttribute("proteins", proteinRepo.findAll());
		return "register.html";
	}
	
	
	private String encodePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
}
