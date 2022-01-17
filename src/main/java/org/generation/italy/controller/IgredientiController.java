package org.generation.italy.controller;

import java.util.List;

import javax.validation.Valid;

import org.generation.italy.model.Ingredienti;
import org.generation.italy.model.Pizza;
import org.generation.italy.service.IngredientiService;
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
@RequestMapping("/ingredienti")
public class IgredientiController {
	
	@Autowired
	private IngredientiService ingrService;
	
	
	@GetMapping
	public String list(Model model, @RequestParam(name="keyword", required=false) String keyword) {
		List<Ingredienti> result;
		if(keyword != null && !keyword.isEmpty()) {
			result = ingrService.findByKeywordSortedByRecente(keyword);
			model.addAttribute("keyword", keyword);
		}else {
			result = ingrService.findAllSortByIngredienti();
		}
		model.addAttribute("ingredienti", result);	
	
		return"/ingredienti/info";
	}


	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("edit", false);
		model.addAttribute("ingredienti", new Ingredienti());
		model.addAttribute("ingredientiList", ingrService.findAllSortByIngredienti());
		return "/ingredienti/edit";
	}
	
	@PostMapping("/docreate")
	public String doCreate(@Valid @ModelAttribute("ingredienti") Ingredienti formIngredienti, BindingResult bindingresult, Model model) {
		if(bindingresult.hasErrors()) {
			model.addAttribute("edit", false);
			model.addAttribute("ingredientiList", ingrService.findAllSortByIngredienti());
			return "/ingredienti/info";
		}
		ingrService.save(formIngredienti);
		return "redirect:/ingredienti";		
	}
	
	@GetMapping("/delete/{id}")
	public String doDelete(@PathVariable("id") Integer id) {		
		ingrService.deleteById(id);
		return "redirect:/ingredienti";
	}
	
	@GetMapping("/edit/{id}")
		public String edit(@PathVariable ("id") Integer id, Model model) {
			model.addAttribute("edit", true);
			model.addAttribute("ingredienti", ingrService.getById(id));		
			model.addAttribute("ingredientiList", ingrService.findAllSortByIngredienti());
			return "/ingredienti/edit";
	}
		
	@PostMapping("/edit/{id}")
	public String doUpdate(@Valid @ModelAttribute("pizza") Ingredienti formIngredienti, BindingResult bindingresult, Model model) {
	
		if(bindingresult.hasErrors()) {
			model.addAttribute("edit", true);
			model.addAttribute("ingredientiList", ingrService.findAllSortByIngredienti());
			return "/ingredienti/edit";
		}
		ingrService.update(formIngredienti);
		
		return "redirect:/pizza";
	}
	

}
