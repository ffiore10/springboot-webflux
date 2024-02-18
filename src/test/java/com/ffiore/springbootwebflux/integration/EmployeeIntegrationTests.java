package com.ffiore.springbootwebflux.integration;

import com.ffiore.springbootwebflux.dto.EmployeeDto;
import com.ffiore.springbootwebflux.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

// i test di integrazione e le API funzionano solo tirando su un'immagine in locale di mongodb
// in alternativa si usa la classe EmployeeIntegrationTestsWithTestsContainer con un testContainer con mongo pre lanciato
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeIntegrationTests {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void setup(){
        // precondition
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("Francesco")
                .lastName("Fiore")
                .email("francesco@gmail.com")
                .build();
        EmployeeDto employeeDto2 = EmployeeDto.builder()
                .firstName("John")
                .lastName("Cena")
                .email("cena@tiscali.com")
                .build();
    }

    @Test
    public void givenEmployee_whenSaveEmployee_returnEmployeeSave(){

        // given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("test")
                .lastName("test")
                .email("test@test.com")
                .build();

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
    public void givenEmployee_whenSearchEmployeeById_returnEmployeeFound(){

        // given
        String employeeId = "656e60becc587c4220d64214";

        // mock with mockito mock standard way
        WebTestClient.ResponseSpec responseSpec = webTestClient.get().uri("/api/employees/{id}", Collections.singletonMap("id", employeeId))
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        // then
        responseSpec.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo("test")
                .jsonPath("$.lastName").isEqualTo("test");

    }

    @Test
    public void givenEmployees_whenSearchAllEmployee_returnAllEmployeesFound(){

        // given

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
                .firstName("Dave")
                .lastName("Batista")
                .email("batista@gmail.com")
                .build();
        String id = "656e60becc587c4220d64214";
        // mock with mockito mock standard way
        WebTestClient.ResponseSpec responseSpec = webTestClient.put().uri("/api/employees/{id}", Collections.singletonMap("id", id))
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
    public void givenEmployee_whenDeleteEmployee_returnMonoVoid(){

        // given
        String id = "653fdfdc3fed4652a08c13e7";
        // mock with mockito mock standard way
        WebTestClient.ResponseSpec responseSpec = webTestClient.delete().uri("/api/employees/{id}", Collections.singletonMap("id", id))
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        // then
        responseSpec.expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);

    }
}
