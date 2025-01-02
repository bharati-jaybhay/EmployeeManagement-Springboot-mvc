package com.mvc.employee.service;

import com.mvc.employee.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(int id);

    Employee addEmployee(Employee employee);

    void deleteEmployeeById(int id);

    Page<Employee> getAllEmployeeswithpage(int pageNum, String sortField, String sortDir);
}
