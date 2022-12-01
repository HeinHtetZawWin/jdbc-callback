package com.example.jdbccallback.ds;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Employee {
    private String firstName;
    private String lastName;
    private String email;
    private double salary;
}
