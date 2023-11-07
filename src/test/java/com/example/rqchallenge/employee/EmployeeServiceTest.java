package com.example.rqchallenge.employee;

import com.example.rqchallenge.employee.controllers.EmployeeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmployeeServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private EmployeeController employeeController;

    private final Employee mockEmployeeOne = Employee.builder()
            .id("1")
            .name("Test Employee")
            .salary(100000)
            .age(30)
            .build();

    private final Employee mockEmployeeTwo = Employee.builder()
            .id("2")
            .name("Employee 2")
            .salary(50000)
            .age(24)
            .build();

    @Test
    public void getAllEmployees_ReturnsValidResponse() throws IOException {
        List<Employee> mockEmployees = new ArrayList<>();
        mockEmployees.add(mockEmployeeOne);
        mockEmployees.add(mockEmployeeTwo);

        when(restTemplate.exchange("https://dummy.restapiexample.com/api/v1/employees", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Employee>>() {}))
                .thenReturn(ResponseEntity.ok(mockEmployees));
        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        assertEquals(200, response.getStatusCodeValue());
        List<Employee> actualEmployees = response.getBody();
        assertNotNull(actualEmployees);
        assertEquals(2, actualEmployees.size());
        assertEquals("Test Employee", actualEmployees.get(0).getName());
        assertEquals("Employee 2", actualEmployees.get(1).getName());
    }

    @Test
    public void getEmployeeById_ReturnsValidResponse() {
        when(restTemplate.getForEntity("https://dummy.restapiexample.com/api/v1/employee/1", Employee.class))
                .thenReturn(ResponseEntity.ok(mockEmployeeOne));
        ResponseEntity<Employee> response = employeeController.getEmployeeById("1");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("1", response.getBody().getId());
        assertEquals("Test Employee", response.getBody().getName());
        assertEquals(100000, response.getBody().getSalary());
        assertEquals(30, response.getBody().getAge());
    }
}

