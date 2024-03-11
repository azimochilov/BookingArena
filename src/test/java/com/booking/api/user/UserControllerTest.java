package com.booking.api.user;


import com.booking.domain.dtos.users.UserCreationDto;
import com.booking.domain.dtos.users.UserUpdateDto;
import com.booking.domain.dtos.users.VerifyDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class UserControllerTest {

    private WebTestClient client;

    @Autowired
    private WebApplicationContext wac;

    private UserCreationDto userCreationDto;
    private UserUpdateDto userUpdateDto;
    private VerifyDto verifyDto;

    @BeforeEach
    void setUp() {
        client = MockMvcWebTestClient.bindToApplicationContext(this.wac)
                .configureClient()
                .baseUrl("http://localhost:8081/api/v1/users")
                .build();

        userCreationDto = new UserCreationDto();
        userUpdateDto = new UserUpdateDto();
        verifyDto = new VerifyDto();
    }

    @Test
    @Order(1)
    void registerUser_ReturnStatusOk() {
        client.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(userCreationDto), UserCreationDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody();

    }

    @Test
    @Order(2)
    void updateUser_ReturnStatusCreated() {
        Long userId = 1L;
        client.patch().uri("/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(userUpdateDto), UserUpdateDto.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    @Order(3)
    void verifyUser_ReturnStatusOkOrBadRequest() {
        Long userId = 1L;
        client.post().uri("/{id}/verify", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(verifyDto), VerifyDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(4)
    void resendVerificationCode_ReturnStatusOk() {
        Long userId = 1L;
        client.post().uri("/{id}/resend", userId)
                .exchange()
                .expectStatus().isOk();
    }
}