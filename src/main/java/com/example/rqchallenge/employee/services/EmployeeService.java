package com.example.rqchallenge.employee.services;

import com.example.rqchallenge.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeService {

    private final String BASE_URL = "https://dummy.restapiexample.com/api/v1/";

    @Autowired
    private final RestTemplate restTemplate;

    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<Employee> getEmployeeById(String id) {
        String url = BASE_URL + "employee/" + id;
        return restTemplate.getForEntity(url, Employee.class);
    }
}
