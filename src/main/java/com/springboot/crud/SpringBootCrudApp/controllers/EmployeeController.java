package com.springboot.crud.SpringBootCrudApp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.crud.SpringBootCrudApp.models.Employee;
import com.springboot.crud.SpringBootCrudApp.services.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeService service;
	
	
	@GetMapping({"/", "/viewEmployees"})
	public String viewEmployees(@ModelAttribute("message") String message, Model model) {
		
		List<Employee> employeeList = service.listAllEmployees();
		model.addAttribute("employeeList", employeeList);
		model.addAttribute("message", message);
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
			redirectAttributes.addFlashAttribute("message", "Employee successfully saved");
			return "redirect:/viewEmployees";
		}
		
		
		redirectAttributes.addFlashAttribute("message", "Failed to save the Employee");
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
			
			redirectAttributes.addFlashAttribute("message", "The employee has been edited");
			return "redirect:/viewEmployees";
		}
		
		redirectAttributes.addFlashAttribute("message", "Failed to edit the Employee");
		return "redirect:/editEmployee/" + employee.getId();
		
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		
		if (service.deleteEmployee(id)) {
			redirectAttributes.addFlashAttribute("message", "Employee has been deleted");
			return "redirect:/viewEmployees";
		}
		
		
		redirectAttributes.addFlashAttribute("message", "Failed to delete the Employee");
		return "redirect:/viewEmployees";
		
		
	}
	
}
