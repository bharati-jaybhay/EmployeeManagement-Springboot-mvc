package com.mvc.employee.service;


import com.mvc.employee.entity.Employee;
import com.mvc.employee.repository.EmployeeRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepos employeeRepos;

    private final int PAGE_SIZE = 3;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepos employeeRepos) {
        this.employeeRepos = employeeRepos;
    }

    @Override
    public List<Employee> getAllEmployees() {
        // return employeeRepository.findAll();
        return employeeRepos.findAllByOrderByFirstNameAsc();
    }

    @Override
    public Page<Employee> getAllEmployeeswithpage(int pageNum, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
                : Sort.by(
                sortField).descending();
        Pageable pageable = PageRequest.of(pageNum - 1, PAGE_SIZE, sort);
        Page<Employee> pages = employeeRepos.findAll(pageable);

        return pages;
    }

    @Override
    public Employee getEmployeeById(int id) {
        Optional<Employee> result = employeeRepos.findById(id);
        Employee theEmployee = null;
        if (result.isPresent()) {
            theEmployee = result.get();
        }
        return theEmployee;
    }

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepos.save(employee);
    }

    @Override
    public void deleteEmployeeById(int id) {
        employeeRepos.deleteById(id);
    }
}