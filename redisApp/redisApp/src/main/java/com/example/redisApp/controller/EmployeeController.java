package com.example.redisApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.redisApp.model.EmployeeModel;
import com.example.redisApp.services.EmployeeService;
import java.util.*;
@RestController
@RequestMapping("v1/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeservice;
    @PostMapping("/")
    public ResponseEntity<EmployeeModel> saveEmployee(@RequestBody EmployeeModel employeeModel)
    {
        System.out.println(employeeModel.getEmp_id());
        EmployeeModel storeEmployeeModel=employeeservice.savEmployeeModel(employeeModel);
        return ResponseEntity.ok(storeEmployeeModel);
        
    }

    @GetMapping("/")
    public Map<Object,Object> getAllEmployee()
    {
        return employeeservice.getAllEmployee(); 
    }

}
