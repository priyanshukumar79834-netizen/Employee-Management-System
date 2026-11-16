package com.kiet.employee.controller;

import com.kiet.employee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final List<Employee> employeeList = new ArrayList<>();

    // (b) Add a new employee using @PostMapping and @RequestBody
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        employeeList.add(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    // (c) Retrieve all employees using @GetMapping
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    // (c) Retrieve a specific employee by ID using @GetMapping
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
        
        if (employee.isPresent()) {
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // (d) Update an employee's details using @PutMapping
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedDetails) {
        Optional<Employee> employeeOpt = employeeList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();

        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            employee.setName(updatedDetails.getName());
            employee.setDepartment(updatedDetails.getDepartment());
            employee.setSalary(updatedDetails.getSalary());
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // (e) Delete an employee by ID using @DeleteMapping
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        boolean removed = employeeList.removeIf(e -> e.getId().equals(id));
        
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
