package com.springboot.crud.SpringBootCrudApp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.crud.SpringBootCrudApp.models.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	@Query("select e from Employee e where e.name like %?1%")
	List<Employee> findByName(String name);


}
