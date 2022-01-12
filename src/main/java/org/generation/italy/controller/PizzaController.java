package org.generation.italy.controller;



import javax.validation.Valid;

import org.generation.italy.model.Pizza;
import org.generation.italy.service.IngredientiService;
import org.generation.italy.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pizza")
public class PizzaController {
	
	@Autowired
	private PizzaService service;
	
	@Autowired
	private IngredientiService ingredientiService;
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("list", service.findAllThePizza());
		return"/pizza/lista";
	}


	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("edit", false);
		model.addAttribute("pizza", new Pizza());
		model.addAttribute("ingredientiList", ingredientiService.findAllSortByIngredienti());
		return "/pizza/edit";
	}
	
	@PostMapping("/docreate")
	public String doCreate(@ModelAttribute("pizza") Pizza formPizza, Model model) {
		
		service.save(formPizza);
		return "redirect:/pizza";		
	}
	
	@GetMapping("/delete/{id}")
	public String doDelete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("successMessage", "Pizza deleted!");
		service.deleteById(id);
		return "redirect:/pizza";
	}
	
	@GetMapping("/edit/{id}")
		public String edit(@PathVariable ("id") Integer id, Model model) {
		model.addAttribute("edit", true);
			model.addAttribute("pizza", service.getById(id));
			model.addAttribute("pizza", new Pizza());
			model.addAttribute("ingredientiList", ingredientiService.findAllSortByIngredienti());
			return "/pizza/edit";
	}
		
	@PostMapping("/edit/{id}")
	public String doUpdate(@ModelAttribute("pizza") Pizza formPizza, BindingResult bindingresult, Model model) {
		service.update(formPizza);
		return "redirect:/pizza";
	}
		
	
	
	

	
	}
	


