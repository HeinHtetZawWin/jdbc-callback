package com.example.jdbccallback.service;

import com.example.jdbccallback.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;


    public void run() {
        System.out.println("List all emails...");
        employeeDao.listEmail().forEach(System.out::println);
        System.out.println();
        System.out.println("list all employees");
        employeeDao.listEmployee().forEach(System.out::println);
        System.out.println();
        System.out.println("Average RowCallbackHandler");
        System.out.println("Employee's Salary Average::" +
                employeeDao.averageEmployeeSalaryRowCallBackHandler());
        System.out.println();
        System.out.println("Average ResultSetExtractor");
        System.out.println("Employee's Salary Average::" +
                employeeDao.averageEmployeeSalaryResultSetExtractor());
        System.out.println();
        System.out.println("Average Database Level::");
        System.out.println("Employee's Salary Average::" + employeeDao.averageDatabaseLevel());
        System.out.println();
        System.out.println("Average Database Modern Way::");
        System.out.println("Employee's Salary Average Modern Way::" + employeeDao.averageModernWay());
    }
}
