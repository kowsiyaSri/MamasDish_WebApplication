package ca.sheridancollege.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.Chef;
import ca.sheridancollege.beans.EndUser;
import ca.sheridancollege.beans.Recipe;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.repositories.ChefRepository;
import ca.sheridancollege.repositories.CountryRepository;
import ca.sheridancollege.repositories.CuisineRepository;
import ca.sheridancollege.repositories.DietRepository;
import ca.sheridancollege.repositories.EndUserRepository;
import ca.sheridancollege.repositories.MealTypeRepository;
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

		} else if(userRepo.findByUsername(email) != null){
			
			model.addAttribute("errMssg", "Email already registered.");
			model.addAttribute("emailInput", email);
			model.addAttribute("fNameInput", fname);
			model.addAttribute("lNameInput", lname);


			return "register.html";
		} else {
			EndUser endUser = EndUser.builder().firstName(fname).lastName(lname).email(email).password(password)
					.build();
			User user = new User(email, encodePassword(password));
			endUserRepo.save(endUser);
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
