package com.example.rqchallenge.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Employee {

    public String id;
    public String name;
    public int salary;
    public int age;
}
