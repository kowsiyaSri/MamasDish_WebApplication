package ca.sheridancollege.controllers;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ca.sheridancollege.beans.Instruction;
import ca.sheridancollege.email.Email;
import ca.sheridancollege.repositories.RecipeRepository;

@Controller
public class AdminController {
	
	@Autowired
	private RecipeRepository recipeRepo;
	
	@Autowired
	private Email email;
	
	@GetMapping("/admin")
	public String index(Model model) {
		
		model.addAttribute("recipes", recipeRepo.findByAuthFalse());
		
		return "/admin/index.html";
	}
	
	@GetMapping("/admin/authRecipe/{id}")
	public String authRecipe(Model model, @PathVariable int id) {
		
		model.addAttribute("recipe", recipeRepo.findById(Long.valueOf(id)).get());
		List<Instruction> instruct = recipeRepo.findById(Long.valueOf(id)).get().getInstructions();
		instruct.sort(Comparator.comparing(Instruction::getStepNumber));
		model.addAttribute("instructions", instruct);
		
		return "/admin/authRecipe.html";
	}
	
	
}
