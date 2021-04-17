package ca.sheridancollege.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.Recipe;
import ca.sheridancollege.repositories.CountryRepository;
import ca.sheridancollege.repositories.CuisineRepository;
import ca.sheridancollege.repositories.DietRepository;
import ca.sheridancollege.repositories.MealTypeRepository;
import ca.sheridancollege.repositories.RecipeRepository;

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
	
	@GetMapping("/")
	public String home(Model model) {
		return "home.html";
	}
	
	@GetMapping("/uploadRecipe")
	public String goUploadRecipe(Model model){
		model.addAttribute("recipe", new Recipe());
		model.addAttribute("countries", countryRepo.findByOrderByName());
		model.addAttribute("cuisines", cuisineRepo.findByOrderByCuisineName());
		model.addAttribute("meals", mealRepo.findAll());
		model.addAttribute("diets", dietRepo.findAll());
		return "recipe.html";
	}
	
	@PostMapping("/addRecipe")
	public String addRecipe(@ModelAttribute Recipe recipe, @RequestParam String prep, @RequestParam String cook) {
		String ptime[] = prep.split(":"); 
		float phr =Float.parseFloat(ptime[0]) * 60; 
		float pmin = Float.parseFloat(ptime[1]);
		recipe.setPrepTime(phr + pmin);
		
		String ctime[] = cook.split(":");
		float chr =Float.parseFloat(ctime[0]) * 60; 
		float cmin = Float.parseFloat(ctime[1]);
		recipe.setCookTime(chr + cmin);
		System.out.println(recipe.getPrepTime());
		System.out.println(recipe.getCookTime());
		return "redirect:/uploadRecipe";
	}
}
