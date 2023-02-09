package com.springboot.crud.SpringBootCrudApp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.crud.SpringBootCrudApp.models.Employee;
import com.springboot.crud.SpringBootCrudApp.repositories.EmployeeRepository;
import com.springboot.crud.SpringBootCrudApp.services.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeService service;
	
	@Autowired
	EmployeeRepository repository;
	
	
	@GetMapping({"/", "/viewEmployees"})
	public String viewEmployees(@ModelAttribute("message") String message, Model model, ModelMap modelMap) {
		
		List<Employee> employeeList = service.listAllEmployees();
		model.addAttribute("employeeList", employeeList);
		model.addAttribute("message", message);
		modelMap.addAttribute("employeeList", service.listAllEmployees());
		return "ViewEmployees";
	}
		
	@GetMapping("/addEmployee")
	public String newEmployee(@ModelAttribute("message") String message, Model model) {
		
		model.addAttribute("employee", new Employee());
		model.addAttribute("message", message);
		return "AddEmployee";
		
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(Employee employee, RedirectAttributes redirectAttributes) {
		
		if (service.saveOrUpdateEmployee(employee)) {
			redirectAttributes.addFlashAttribute("message", "Save Success");
			return "redirect:/viewEmployees";
		}
		
		
		redirectAttributes.addFlashAttribute("message", "Save Failed");
		return "redirect:/addEmployee";
	}
	
	@GetMapping("/editEmployee/{id}") 
	public String editEmployee(@PathVariable Long id, String message, Model model) {
		Employee emp = service.getEmployeeId(id);
		model.addAttribute("emp", emp);
		model.addAttribute("message", message);
		return "EditEmployee";
		
	}
	
	@PostMapping("/editSaveEmployee")
	public String editSaveEmployee(Employee employee, RedirectAttributes redirectAttributes) {
		
		if (service.saveOrUpdateEmployee(employee) ) {
			
			redirectAttributes.addFlashAttribute("message", "Edit Success");
			return "redirect:/viewEmployees";
		}
		
		redirectAttributes.addFlashAttribute("message", "Edit Failed");
		return "redirect:/editEmployee/" + employee.getId();
		
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		
		if (service.deleteEmployee(id)) {
			redirectAttributes.addFlashAttribute("message", "Delete Success");
			return "redirect:/viewEmployees";
		}
		
		
		redirectAttributes.addFlashAttribute("message", "Delete Failed");
		return "redirect:/viewEmployees";
		
		
	}
	
//	@GetMapping
//	public String getByName(@RequestParam("name") String name, ModelMap model) {
//		
//		model.addAttribute("employeeList", service.searchByName(name));
//		
//		return "ViewEmployee";
//	}
	
	@PostMapping("**/searchemployee")
	public ModelAndView search(@RequestParam("namesearch") String namesearch) {
		
		ModelAndView modelAndView = new ModelAndView("/ViewEmployees");
		modelAndView.addObject("employeeList", repository.findByName(namesearch));

		
		return modelAndView; // ver se nao precis aadd o obj
	}
	
}
