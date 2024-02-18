package com.ffiore.springbootwebflux.configuration;

import com.ffiore.springbootwebflux.repository.EmployeeRepository;
import com.ffiore.springbootwebflux.service.EmployeeService;
import com.ffiore.springbootwebflux.service.impl.EmployeeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.support.SimpleReactiveMongoRepository;

@Configuration
public class EmployeeBootConfiguration {

    @Bean
    public EmployeeService employeeService(EmployeeRepository employeeRepository){
        return new EmployeeServiceImpl(employeeRepository);
    }
}
