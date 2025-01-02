package com.mvc.employee.controller;

import com.mvc.employee.entity.Employee;
import com.mvc.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
//import jakarta.validation.Valid;


import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // add mapping for "/list"
    @GetMapping("/list/{pageNum}")
    public String listEmployeeWithSortAndPagination(@PathVariable int pageNum,
                                                    @RequestParam(name = "sortField") String sortField,
                                                    @RequestParam(name = "sortDir") String sortDir, Model theModel) {

        // get the employee from db
        Page<Employee> pages = employeeService.getAllEmployeeswithpage(pageNum, sortField, sortDir);

        List<Employee> employees = pages.getContent();

        // add to the spring model
        theModel.addAttribute("employees", employees);
        theModel.addAttribute("pageNo", pages.getNumber());
        theModel.addAttribute("totalElements", pages.getTotalElements());
        theModel.addAttribute("elementPerPage", pages.getNumberOfElements());
        theModel.addAttribute("totalPages", pages.getTotalPages());
        theModel.addAttribute("pageSize", pages.getSize());
        theModel.addAttribute("sortField", sortField);
        theModel.addAttribute("sortDir", sortDir);
        theModel.addAttribute("reverseSortDir", sortDir.equalsIgnoreCase("ASC") ? "Desc" : "Asc");

        return "employees/list-employees";
    }

    @GetMapping("/list")
    public String listEmployee(Model theModel) {

         // get the employee from db
         List<Employee> employees = employeeService.getAllEmployees();

         // add to the spring model
         theModel.addAttribute("employees", employees);

        return listEmployeeWithSortAndPagination(1, "id", "Asc", theModel);
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        // create model attribute to bind form data
        Employee theEmployee = new Employee();

        theModel.addAttribute("employee", theEmployee);
        employeeService.addEmployee(theEmployee);
        return "employees/employee-form";

    }

    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee theEmployee,
                               BindingResult theBindingResult) {
        if (theBindingResult.hasErrors()) {
            return "employees/employee-form";
        } else {
            // save the employee
            employeeService.addEmployee(theEmployee);
            // use redirect to prevent duplicate submissions

            return "redirect:/employees/list";
        }

    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel) {
        // get the employee from the service
        Employee theEmployee = employeeService.getEmployeeById(theId);

        // set employee as a model attribute to pre-populate the form
        theModel.addAttribute("employee", theEmployee);

        // send over to our form
        return "employees/employee-form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId") int theId) {
        // delete the employee
        employeeService.deleteEmployeeById(theId);

        // send over to our form
        return "redirect:/employees/list";
    }
}