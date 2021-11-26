package ca.sheridancollege.controllers;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.EndUser;
import ca.sheridancollege.beans.Instruction;
import ca.sheridancollege.beans.MessageSystem;
import ca.sheridancollege.beans.Recipe;
import ca.sheridancollege.email.Email;
import ca.sheridancollege.repositories.EndUserRepository;
import ca.sheridancollege.repositories.MessageRepository;
import ca.sheridancollege.repositories.RecipeRepository;
import ca.sheridancollege.repositories.UserRepository;

@Controller
public class AdminController {

	@Autowired
	private RecipeRepository recipeRepo;

	@Autowired
	private Email email;
	
	@Autowired
	private MessageRepository mssgRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private EndUserRepository endUserRepo;

	@GetMapping("/admin")
	public String index(Model model) {

		model.addAttribute("recipes", recipeRepo.findByAuthFalseAndCompleteTrue());

		return "admin/index.html";
	}

	@GetMapping("/admin/authRecipe/{id}")
	public String authRecipe(Model model, @PathVariable int id) {

		model.addAttribute("recipe", recipeRepo.findById(Long.valueOf(id)).get());
		List<Instruction> instruct = recipeRepo.findById(Long.valueOf(id)).get().getInstructions();
		instruct.sort(Comparator.comparing(Instruction::getStepNumber));
		model.addAttribute("instructions", instruct);

		return "admin/authRecipe.html";
	}

	@PostMapping("/reject")
	public String rejectRecipt(@RequestParam(value = "reason[]") String[] reasons, @RequestParam String expl,
			@RequestParam int recipeId, Model model) {

		Recipe recipe = recipeRepo.findById(Long.valueOf(recipeId)).get();
		recipe.setAuth(false);
		String chefEmail = recipe.getChef().getEnduser().getEmail();
		String recipeTitle = recipe.getTitle();
		String subject = recipeTitle + " has been rejected.";
		String body = "Reasons for rejection: \n ";

		for (String reas : reasons) {

			body += reas + " | ";
		}

		body += "\n"+ expl;

		email.sendEmail(chefEmail, subject, body);

		model.addAttribute("recipes", recipeRepo.findByAuthFalse());
		
		MessageSystem mssg = new MessageSystem();
		mssg.setSubject(recipeTitle + " has been rejected.");
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

		return "admin/index.html";
	}

}
