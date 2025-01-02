package com.mvc.employee.repository;

import com.mvc.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EmployeeRepos extends JpaRepository<Employee, Integer> {

    public List<Employee> findAllByOrderByFirstNameAsc();
}
