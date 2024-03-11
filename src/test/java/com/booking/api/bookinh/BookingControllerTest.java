package com.booking.api.bookinh;

import com.booking.domain.dtos.booking.ReservationArenaCreationDto;
import com.booking.domain.dtos.booking.ReservationArenaResultDto;
import org.junit.jupiter.api.*;
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
public class BookingControllerTest {
    private ReservationArenaCreationDto reservationArenaCreationDto;
    private WebTestClient client;
    @Autowired
    private WebApplicationContext wac;
    @BeforeEach
    void setUp() {
        client = MockMvcWebTestClient.bindToApplicationContext(this.wac)
                .configureClient()
                .baseUrl("http://localhost:8081/api/bookings")
                .build();
        reservationArenaCreationDto = new ReservationArenaCreationDto();
    }


    @Test
    @Order(1)
    void bookArena_ReturnStatusCreated() {
        client.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(reservationArenaCreationDto), ReservationArenaCreationDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ReservationArenaResultDto.class);
    }

    @Test
    @Order(2)
    void getAllBookings_ReturnStatusOk() {
        client.get()
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ReservationArenaResultDto.class);
    }
}
