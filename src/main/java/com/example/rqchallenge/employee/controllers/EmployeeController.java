package com.example.rqchallenge.employee.controllers;

import com.example.rqchallenge.employee.Employee;
import com.example.rqchallenge.employee.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController implements IEmployeeController {

    final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
        return employeeService.getAllEmployees();
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {

        return employeeService.getEmployeesByNameSearch(searchString);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        return employeeService.getEmployeeById(id);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return null;
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return null;
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        return null;
    }
}