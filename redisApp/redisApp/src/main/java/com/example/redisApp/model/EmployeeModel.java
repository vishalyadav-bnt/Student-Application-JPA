package com.example.redisApp.model;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Employee")
public class EmployeeModel implements Serializable {
   
    private int emp_id;
    private String emp_name;
    private String emp_sal; 

}
