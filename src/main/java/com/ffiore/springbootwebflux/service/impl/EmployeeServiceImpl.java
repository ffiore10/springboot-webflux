package com.ffiore.springbootwebflux.service.impl;

import com.ffiore.springbootwebflux.dto.EmployeeDto;
import com.ffiore.springbootwebflux.entity.Employee;
import com.ffiore.springbootwebflux.mapper.EmployeeMapper;
import com.ffiore.springbootwebflux.repository.EmployeeRepository;
import com.ffiore.springbootwebflux.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Getter
@Setter
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository repository;
    @Override
    public Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto) {
        Mono<Employee> savedEmployee = repository.save(EmployeeMapper.convertoDtoToEmployee(employeeDto));
        return savedEmployee.
                map((employee -> EmployeeMapper.convertoDtoToEmployee(employee)));
    }

    @Override
    public Mono<EmployeeDto> getEmployeeById(String employeeId) {
        Mono<Employee> employee = repository.findById(employeeId);

        return employee.
                map(employeeEntity -> EmployeeMapper.convertoDtoToEmployee(employeeEntity));
    }

    @Override
    public Flux<EmployeeDto> getAllEmployees() {
        Flux<Employee> employees = repository.findAll();
        return employees
            .map(employee -> EmployeeMapper.convertoDtoToEmployee(employee))
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<EmployeeDto> updateEmployee(String employeeId, EmployeeDto updatedEmploy) {

        Mono<Employee> employee = repository.findById(employeeId);
        Mono<Employee> updatedEmployee = employee.flatMap( existingEmployee -> {
            return repository.save(Employee.builder()
                    .id(employeeId)
                    .firstName(updatedEmploy.getFirstName())
                    .lastName(updatedEmploy.getLastName())
                    .email(updatedEmploy.getEmail())
                    .build());
        });

        return updatedEmployee
                .map(employee1 -> EmployeeMapper.convertoDtoToEmployee(employee1));
    }

    @Override
    public Mono<Void> deleteEmployee(String employeeId) {
        return repository.deleteById(employeeId);
    }


}

