package org.generation.italy.controller;

import org.generation.italy.model.Pizza;
import org.generation.italy.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pizza")
public class PizzaController {
	
	@Autowired
	private PizzaService service;
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("list", service.findAllThePizza());
		return"/pizza/lista";
	}


	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("pizza", new Pizza());
		return "/pizza/edit";
	}
	
	@PostMapping("/docreate")
	public String doCreate(@ModelAttribute("pizza") Pizza formPizza, Model model) {
		
		service.save(formPizza);
		return "redirect:/pizza";		
	}
	
	
}

