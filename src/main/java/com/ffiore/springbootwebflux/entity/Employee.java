package com.ffiore.springbootwebflux.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employees")
@Getter
@Setter
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
}
