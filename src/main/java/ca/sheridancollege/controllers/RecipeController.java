package ca.sheridancollege.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ca.sheridancollege.beans.Recipe;
import ca.sheridancollege.repositories.RecipeRepository;

@Controller
public class RecipeController {

	@Autowired
	private RecipeRepository recipeRepo;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("recipes", recipeRepo.findAll());
		//Recipe r = recipeRepo.findById(Long.valueOf(1)).get();
		return "home.html";
	}
}
