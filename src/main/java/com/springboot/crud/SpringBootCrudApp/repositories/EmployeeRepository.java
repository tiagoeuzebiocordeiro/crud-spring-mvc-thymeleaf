package com.springboot.crud.SpringBootCrudApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.crud.SpringBootCrudApp.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{


}
