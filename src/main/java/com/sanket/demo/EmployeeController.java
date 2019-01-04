package com.sanket.demo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
class EmployeeController {
    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository employeeRepository){
        this.repository = employeeRepository;
    }

    // Aggregate root

    @GetMapping
    ResponseEntity<List<Employee>> all() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    Employee newEmployee(@RequestBody Employee newEmployee){
        return repository.save(newEmployee);
    }

    // Single item

    @GetMapping("/{id}")
    Employee one(@PathVariable Long id) {
        return repository.findById(id)
        .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

} 