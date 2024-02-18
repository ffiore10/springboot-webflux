package com.ffiore.springbootwebflux.integration;

import com.ffiore.springbootwebflux.dto.EmployeeDto;
import com.ffiore.springbootwebflux.service.EmployeeService;
import com.github.dockerjava.api.model.PortBinding;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@Testcontainers
//@EnableAutoConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeIntegrationTestsWithTestsContainer {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private WebTestClient webTestClient;

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest")
            .withExposedPorts(27017)
            .withCreateContainerCmdModifier(cmd -> cmd.withPortBindings(PortBinding.parse("27017:27017")));


    @BeforeAll
    public void setup(){

        mongoDBContainer.start();

        String host = mongoDBContainer.getHost();
        int port = mongoDBContainer.getMappedPort(27017);
        log.info("host: {}", host);
        log.info("port: {}", port);

        EmployeeDto employeeDto = EmployeeDto.builder()
                .id("653fdfdc3fed4652a08c13e7")
                .firstName("Francesco")
                .lastName("Fiore")
                .email("flower@gmail.com")
                .build();

        EmployeeDto employeeDto1 = EmployeeDto.builder()
                .id("6540e36f481b44218ac65ede")
                .firstName("Dave")
                .lastName("Batista")
                .email("batistsa@gmail.com")
                .build();

        webTestClient.post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange();

        webTestClient.post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto1), EmployeeDto.class)
                .exchange();
    }

    @AfterAll
    public void destroyMongoDbConnection(){
        mongoDBContainer.stop();
    }

    @Test
    public void givenEmployee_whenSaveEmployee_returnEmployeeSave(){

        // given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("Dwayne")
                .lastName("Johnson")
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
                .jsonPath("$.firstName").isEqualTo("Dwayne")
                .jsonPath("$.lastName").isEqualTo("Johnson")
                .jsonPath("$.email").isEqualTo("test@test.com");

    }

    @Test
    public void givenEmployee_whenSearchEmployeeById_returnEmployeeFound(){

        // given
        String employeeId = "653fdfdc3fed4652a08c13e7";

        // mock with mockito mock standard way
        WebTestClient.ResponseSpec responseSpec = webTestClient.get().uri("/api/employees/{id}", Collections.singletonMap("id", employeeId))
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        // then
        responseSpec.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo("Francesco")
                .jsonPath("$.lastName").isEqualTo("Fiore");

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
        String id = "6540e36f481b44218ac65ede";
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
