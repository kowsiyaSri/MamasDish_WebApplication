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
			@RequestParam(required = false) boolean isChef, @RequestParam(required = false) String description,
			@RequestParam(value = "countries[]") String[] countries,
			@RequestParam(value = "proteins[]") String[] proteins, @RequestParam(value = "diets[]") String[] diets,
			@RequestParam(value = "cuisines[]") String[] cuisines, Model model) {

		// checks if passwords match
		if (!password.equals(password2)) {

			model.addAttribute("errMssg", "Passwords MUST match.");
			model.addAttribute("emailInput", email);
			model.addAttribute("fNameInput", fname);
			model.addAttribute("lNameInput", lname);
			model.addAttribute("countries", countryRepo.findByOrderByName());
			model.addAttribute("cuisines", cuisineRepo.findByOrderByCuisineName());
			model.addAttribute("diets", dietRepo.findAll());
			model.addAttribute("proteins", proteinRepo.findAll());

			return "register.html";

		} // checks if email is already registered
		else if (userRepo.findByUsername(email) != null) {

			model.addAttribute("errMssg", "Email already registered.");
			model.addAttribute("emailInput", email);
			model.addAttribute("fNameInput", fname);
			model.addAttribute("lNameInput", lname);
			model.addAttribute("countries", countryRepo.findByOrderByName());
			model.addAttribute("cuisines", cuisineRepo.findByOrderByCuisineName());
			model.addAttribute("diets", dietRepo.findAll());
			model.addAttribute("proteins", proteinRepo.findAll());

			return "register.html";
		} else {

			// creates and saves a new user
			EndUser endUser = EndUser.builder().firstName(fname).lastName(lname).email(email).password(password).country(new ArrayList<Country>())
					.protein(new ArrayList<Protein>()).cuisine(new ArrayList<Cuisine>()).diet(new ArrayList<Diet>())
					.build();
			User user = new User(email, encodePassword(password));

			if (proteins.length != 0) {
				for (String p : proteins) {
					endUser.getProtein().add(proteinRepo.findByProteinType(p));
				}
			}

			if (cuisines.length != 0) {

				for (String cuisine : cuisines) {
					endUser.getCuisine().add(cuisineRepo.findByCuisineName(cuisine));
				}
			}

			if (countries.length != 0) {

				for (String country : countries) {
					endUser.getCountry().add(countryRepo.findByName(country));
				}
			}
			
			if (diets.length != 0) {

				for (String diet : diets) {
					endUser.getDiet().add(dietRepo.findByDietType(diet));
				}
			}

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
