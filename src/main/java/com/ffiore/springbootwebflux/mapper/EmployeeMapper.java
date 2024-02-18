package com.ffiore.springbootwebflux.mapper;

import com.ffiore.springbootwebflux.dto.EmployeeDto;
import com.ffiore.springbootwebflux.entity.Employee;

public class EmployeeMapper {

    public static Employee convertoDtoToEmployee(EmployeeDto dto){

        return Employee.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail()).build();
    }

    public static EmployeeDto convertoDtoToEmployee(Employee entity){

        return EmployeeDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail()).build();
    }
}
