package com.ffiore.springbootwebflux.controller;

import com.ffiore.springbootwebflux.dto.EmployeeDto;
import com.ffiore.springbootwebflux.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
@Tag(name = "EmployeeController", description = "Controller per operazioni sull'oggetto Employee")
public class EmployeeController {

    private EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "salvataggio di un nuovo employee", description = "Salva un nuovo employee")
    public Mono<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto){
        return employeeService.saveEmployee(employeeDto);
    }

    @GetMapping("{id}")
    public Mono<EmployeeDto> findEmployeeById(@PathVariable("id") String employeeId){
        return employeeService.getEmployeeById(employeeId);
    }

    @GetMapping
    public Flux<EmployeeDto> findAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @PutMapping("{id}")
    public Mono<EmployeeDto> updateEmployee(@PathVariable("id") String employeeId, @RequestBody EmployeeDto updatedEmployee) {
        return employeeService.updateEmployee(employeeId, updatedEmployee);

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEmployee(@PathVariable("id") String employeeId) {
        return employeeService.deleteEmployee(employeeId);

    }
}
