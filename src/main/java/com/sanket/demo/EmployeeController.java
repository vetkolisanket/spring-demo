package com.sanket.demo;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
class EmployeeController {
    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository employeeRepository) {
        this.repository = employeeRepository;
    }

    // Aggregate root

    @GetMapping
    ResponseEntity<List<Employee>> all() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }

    // Single item

    @GetMapping("/{id}")
    Employee one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    /*
     * @PutMapping("/{id}") Employee replacEmployee(@RequestBody Employee
     * newEmployee, @PathVariable Long id) { return repository.findById(id)
     * .map(employee -> { employee.setName(newEmployee.getName());
     * employee.setRole(newEmployee.getRole()); return repository.save(employee); })
     * .orElse(() -> { newEmployee.setId(id); return repository.save(newEmployee);
     * }); }
     */

    @PutMapping("/{id}")
    Employee replacEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        try {
            Employee employee = repository.findById(id).get();
            employee.setName(newEmployee.getName());
            employee.setRole(newEmployee.getRole());
            return repository.save(employee);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            newEmployee.setId(id); 
            return repository.save(newEmployee);
        }
    }

    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }

}