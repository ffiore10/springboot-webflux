package com.ffiore.springbootwebflux.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
public class EmployeeDto {

    private String id;
    private String firstName;
    private String lastName;
    private String email;

}
