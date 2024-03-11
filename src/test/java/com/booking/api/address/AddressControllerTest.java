package com.booking.api.address;

import com.booking.domain.dtos.addresses.AddressCreationDto;
import com.booking.domain.dtos.addresses.AddressResultDto;
import com.booking.domain.dtos.addresses.AddressUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Mono;
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class AddressControllerTest {


    private WebTestClient client;

    @Autowired
    private WebApplicationContext wac;

    private AddressCreationDto addressCreationDto;
    private AddressUpdateDto addressUpdateDto;

    @BeforeEach
    void setUp() {

        client = MockMvcWebTestClient.bindToApplicationContext(this.wac)
                .configureClient()
                .baseUrl("http://localhost:8081")
                .build();
        addressCreationDto = new AddressCreationDto("123 Baker Street", "London", -0.15591514, 51.518969);
        addressUpdateDto = new AddressUpdateDto("124 Baker Street", "London", -0.15591514, 51.518969);
    }
    @Test
    @Order(1)
    @WithMockUser(username = "USER", authorities = {"ADDRESS_SERVICE"})
    void createAddress_ReturnStatusOk() {
        client.post().uri("/addresses")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(addressCreationDto), AddressCreationDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AddressResultDto.class);
    }

    @Test
    @Order(2)
    @WithMockUser(username = "user", authorities = {"ADDRESS_SERVICE"})
    void updateAddress_ReturnStatusOk() {
        Long addressId = 1L;
        client.put().uri("/addresses/" + addressId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(addressUpdateDto), AddressUpdateDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AddressResultDto.class);
    }

    @Test
    @Order(3)
    @WithMockUser(username = "user", authorities = {"ADDRESS_SERVICE"})
    void getAddressById_ReturnStatusOk() {
        client.get().uri("/addresses/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(AddressResultDto.class)
                .returnResult();
    }

    @Test
    @Order(4)
    @WithMockUser(username = "USER", authorities = {"ADDRESS_SERVICE"})
    void deleteAddress_ReturnStatusNoContent() {
        Long addressId = 1L;
        client.delete().uri("/addresses/" + addressId)
                .exchange()
                .expectStatus().isNoContent();
    }
}

