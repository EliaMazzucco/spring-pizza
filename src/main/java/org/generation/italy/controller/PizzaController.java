package org.generation.italy.controller;



import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/pizza")
public class PizzaController {
	
	@Autowired
	private PizzaService service;
	
	@Autowired
	private IngredientiService ingredientiService;
	
	@GetMapping
	public String list(Model model, @RequestParam(name="keyword", required=false) String keyword) {
		List<Pizza> result;
		if(keyword != null && !keyword.isEmpty()) {
			result = service.findByKeywordSortedByRecente(keyword);
			model.addAttribute("keyword", keyword);
		}else {
			result = service.findAllThePizza();
		}
		model.addAttribute("list", result);
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
	public String doCreate(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingresult, Model model) {
		if(bindingresult.hasErrors()) {
			model.addAttribute("edit", false);
			model.addAttribute("ingredientiList", ingredientiService.findAllSortByIngredienti());
			return "/pizza/edit";
		}
		service.save(formPizza);
		return "redirect:/pizza";		
	}
	
	@GetMapping("/delete/{id}")
	public String doDelete(@PathVariable("id") Integer id) {		
		service.deleteById(id);
		return "redirect:/pizza";
	}
	
	@GetMapping("/edit/{id}")
		public String edit(@PathVariable ("id") Integer id, Model model) {
			model.addAttribute("edit", true);
			model.addAttribute("pizza", service.getById(id));		
			model.addAttribute("ingredientiList", ingredientiService.findAllSortByIngredienti());
			return "/pizza/edit";
	}
		
	@PostMapping("/edit/{id}")
	public String doUpdate(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingresult, Model model) {
	
		if(bindingresult.hasErrors()) {
			model.addAttribute("edit", true);
			model.addAttribute("ingredientiList", ingredientiService.findAllSortByIngredienti());
			return "/pizza/edit";
		}
		service.update(formPizza);
		
		return "redirect:/pizza";
	}
		
	@GetMapping("/info/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("pizza", service.getById(id));
		return "/pizza/info";
	}


	
	}
	