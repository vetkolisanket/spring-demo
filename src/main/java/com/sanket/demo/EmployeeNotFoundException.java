package com.sanket.demo;

class EmployeeNotFoundException extends RuntimeException {

    EmployeeNotFoundException(Long id) {
        super("Could not found employee with id " + id);
    }

}