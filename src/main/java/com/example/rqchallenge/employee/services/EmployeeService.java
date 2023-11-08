package com.example.rqchallenge.employee.services;

import com.example.rqchallenge.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
public class EmployeeService {

    private final String BASE_URL = "https://dummy.restapiexample.com/api/v1/";

    @Autowired
    private final RestTemplate restTemplate;

    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<List<Employee>> getAllEmployees() {
        String url = BASE_URL + "employees";
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
    }

    public ResponseEntity<Employee> getEmployeeById(String id) {
        String url = BASE_URL + "employee/" + id;
        return restTemplate.getForEntity(url, Employee.class);
    }

    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        var response = getAllEmployees();
        if (response.getStatusCodeValue() == 200) {
            var allEmployees = response.getBody();
            var filteredEmployees = filterEmployeesByNameSearch(searchString, allEmployees);
            return ResponseEntity.ok(filteredEmployees);
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }

    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        var response = getAllEmployees();
        var highestSalaryOfEmployee = getHighestSalary(response.getBody());
        return ResponseEntity.status(response.getStatusCode()).body(highestSalaryOfEmployee);
    }

    private Integer getHighestSalary(List<Employee> allEmployees) {
        if (allEmployees != null) {
            return allEmployees.stream()
                    .map(Employee::getSalary)
                    .max(Integer::compareTo)
                    .orElse(0);
        } else {
            return null;
        }
    }

    private static List<Employee> filterEmployeesByNameSearch(String searchString, List<Employee> responseBody) {
        if (responseBody != null) {
            return responseBody.stream()
                    .filter(employee -> employee.getName().toLowerCase().contains(searchString.toLowerCase()))
                    .collect(Collectors.toList());
        } else {
            return emptyList();
        }
    }
}
