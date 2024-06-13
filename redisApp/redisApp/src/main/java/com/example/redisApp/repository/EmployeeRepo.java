package com.example.redisApp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.redisApp.model.EmployeeModel;
import java.util.*;
@Repository
public class EmployeeRepo {

    private static final String HASH_KEY = "employee";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public EmployeeModel save(EmployeeModel employeeModel) {
        redisTemplate.opsForHash().put(HASH_KEY, employeeModel.getEmp_id(), employeeModel);
        return employeeModel;
    }

    public Map<Object,Object>findAll()
    {
        return redisTemplate.opsForHash().entries(HASH_KEY);

    }
}
