package com.springboot.crud.SpringBootCrudApp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.crud.SpringBootCrudApp.models.Employee;
import com.springboot.crud.SpringBootCrudApp.repositories.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository repository;
	
	public List<Employee> listAllEmployees() {
		
		List<Employee> list = new ArrayList<>();
		repository.findAll().forEach(employee -> list.add(employee));
		
		return list;
		
	}
	
	public Employee getEmployeeId(Long id) {
		
		return repository.findById(id).get();
		
	}
	
	public boolean saveOrUpdateEmployee(Employee employee) {
		
		Employee emp = repository.save(employee);
		if (emp != null && repository.findById(emp.getId()) != null) {
			return true;
		}
		
		return false;
		
	}
	
	public boolean deleteEmployee(Long id) {
		
		repository.deleteById(id);
		
		if (repository.findById(id) != null) {
			return false;
		}
		
		return true;
		
	}
	
	
}
