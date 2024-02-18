package com.ffiore.springbootwebflux.repository;

import com.ffiore.springbootwebflux.entity.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, String> {

}
