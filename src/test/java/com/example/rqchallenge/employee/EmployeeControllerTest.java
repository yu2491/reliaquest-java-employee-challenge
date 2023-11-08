package com.example.rqchallenge.employee;

import com.example.rqchallenge.employee.controllers.EmployeeController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmployeeControllerTest {

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

    private final Employee mockEmployeeThree = Employee.builder()
            .id("3")
            .name("Bob Jones")
            .salary(350000)
            .age(52)
            .build();

    @BeforeEach
    public void setUp() {
        List<Employee> mockEmployees = new ArrayList<>();
        mockEmployees.add(mockEmployeeOne);
        mockEmployees.add(mockEmployeeTwo);
        mockEmployees.add(mockEmployeeThree);

        when(restTemplate.exchange("https://dummy.restapiexample.com/api/v1/employees", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Employee>>() {}))
                .thenReturn(ResponseEntity.ok(mockEmployees));
    }

    @Test
    public void getAllEmployees_ReturnsValidResponse() throws IOException {
        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        assertEquals(200, response.getStatusCodeValue());
        List<Employee> actualEmployees = response.getBody();
        assertNotNull(actualEmployees);
        assertEquals(3, actualEmployees.size());
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

    @Test
    public void getEmployeesByNameSearch_ReturnsFilteredList() {
        String searchString = "Employee";

        ResponseEntity<List<Employee>> response = employeeController.getEmployeesByNameSearch(searchString);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Employee> filteredEmployees = response.getBody();
        assertNotNull(filteredEmployees);
        assertEquals(2, filteredEmployees.size());
        assertEquals("Test Employee", filteredEmployees.get(0).getName());
        assertEquals("Employee 2", filteredEmployees.get(1).getName());
    }

    @Test
    public void testGetEmployeesByNameSearch_ReturnsEmptyList() {
        String searchString = "NonExistentEmployee";

        ResponseEntity<List<Employee>> response = employeeController.getEmployeesByNameSearch(searchString);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Employee> filteredEmployees = response.getBody();
        assertNotNull(filteredEmployees);
        assertEquals(0, filteredEmployees.size());
    }

    @Test
    public void getHighestSalaryOfEmployees_ReturnsValidResponse() {
        ResponseEntity<Integer> response = employeeController.getHighestSalaryOfEmployees();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(350000, response.getBody());
    }
}

