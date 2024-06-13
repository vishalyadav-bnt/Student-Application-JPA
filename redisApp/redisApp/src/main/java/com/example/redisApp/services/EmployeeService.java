package com.example.redisApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.redisApp.model.EmployeeModel;
import com.example.redisApp.repository.EmployeeRepo;
import java.util.Map;
@Service
public class EmployeeService {
    @Autowired
    EmployeeRepo employeeRepo;

    public EmployeeModel savEmployeeModel(EmployeeModel employeeModel)
    {
        return employeeRepo.save(employeeModel);
    }
     
    public Map<Object,Object>getAllEmployee()
    {
        return employeeRepo.findAll();
    }
}
