package com.ffiore.springbootwebflux.service;

import com.ffiore.springbootwebflux.dto.EmployeeDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {

    public Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto);

    public Mono<EmployeeDto> getEmployeeById(String employeeId);

    public Flux<EmployeeDto> getAllEmployees();

    public Mono<EmployeeDto> updateEmployee(String employeeId, EmployeeDto updatedEmploy);

    public Mono<Void> deleteEmployee(String employeeId);
}
