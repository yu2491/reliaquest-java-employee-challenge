package com.example.rqchallenge.employee;

import com.example.rqchallenge.employee.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmployeeServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void getEmployeeById_ReturnsValidResponse() {
        Employee mockEmployee = Employee.builder()
                .id("1")
                .name("Test Employee")
                .salary(100000)
                .age(30)
                .build();

        when(restTemplate.getForEntity("https://dummy.restapiexample.com/api/v1/employee/1", Employee.class))
                .thenReturn(ResponseEntity.ok(mockEmployee));
        ResponseEntity<Employee> response = employeeService.getEmployeeById("1");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("1", response.getBody().getId());
        assertEquals("Test Employee", response.getBody().getName());
        assertEquals(100000, response.getBody().getSalary());
        assertEquals(30, response.getBody().getAge());
    }
}

