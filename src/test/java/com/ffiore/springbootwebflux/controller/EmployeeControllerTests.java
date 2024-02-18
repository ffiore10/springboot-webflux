package com.ffiore.springbootwebflux.controller;

import com.ffiore.springbootwebflux.dto.EmployeeDto;
import com.ffiore.springbootwebflux.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeController.class)
public class EmployeeControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void givenEmployee_whenSaveEmployee_returnEmployeeSave(){

        // given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("test")
                .lastName("test")
                .email("test@test.com")
                .build();

        // mock with BDDMockito
        given(employeeService.saveEmployee(any(EmployeeDto.class)))
                .willReturn(Mono.just(employeeDto));

        // mock with mockito mock standard way
        WebTestClient.ResponseSpec responseSpec = webTestClient.post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange();

        // then
        responseSpec.expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo("test")
                .jsonPath("$.lastName").isEqualTo("test")
                .jsonPath("$.email").isEqualTo("test@test.com");

    }

    @Test
    public void givenEmployee_whenGetEmployee_returnRetrievedEmployee(){

        // given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("test")
                .lastName("test")
                .email("test@test.com")
                .build();

        String employeeId = "1";

        // mock with BDDMockito
        given(employeeService.getEmployeeById(employeeId))
                .willReturn(Mono.just(employeeDto));

        // mock with mockito mock standard way
        WebTestClient.ResponseSpec responseSpec = webTestClient.get().uri("/api/employees/{id}", Collections.singletonMap("id", employeeId))
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        // then
        responseSpec.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName());

    }

    @Test
    public void givenEmployees_whenGetAllEmployee_returnAllEmployee(){

        // given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("test")
                .lastName("test")
                .email("test@test.com")
                .build();

        EmployeeDto employeeDto1 = EmployeeDto.builder()
                .firstName("test1")
                .lastName("test1")
                .email("test1@test.com")
                .build();

        Flux<EmployeeDto> employeeDtoFlux = Flux.just(employeeDto, employeeDto1);
        // mock with BDDMockito
        given(employeeService.getAllEmployees())
                .willReturn(employeeDtoFlux);

        // mock with mockito mock standard way
        WebTestClient.ResponseSpec responseSpec = webTestClient.get().uri("/api/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        // then
        responseSpec.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);

    }

    @Test
    public void givenEmployee_whenUpdateEmployee_returnUpdatedEmployee(){

        // given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("test")
                .lastName("test")
                .email("test@test.com")
                .build();

        String employeeId = "1";

        // mock with BDDMockito
        given(employeeService.updateEmployee(any(String.class), any(EmployeeDto.class)))
                .willReturn(Mono.just(employeeDto));

        // mock with mockito mock standard way
        WebTestClient.ResponseSpec responseSpec = webTestClient.put().uri("/api/employees/{id}", Collections.singletonMap("id", employeeId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange();

        // then
        responseSpec.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());

    }

    @Test
    public void givenEmployee_whenDeleteEmployee_returnVoid(){

        // given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("test")
                .lastName("test")
                .email("test@test.com")
                .build();

        String employeeId = "1";


        // mock with
        given(employeeService.deleteEmployee(any(String.class)))
                .willReturn(Mono.empty());

        // mock with mockito mock standard way
        WebTestClient.ResponseSpec responseSpec = webTestClient.delete().uri("/api/employees/{id}", Collections.singletonMap("id", employeeId))
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        // then
        responseSpec.expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);

    }

}
