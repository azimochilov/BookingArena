package com.booking.api.arena;

import com.booking.domain.dtos.addresses.AddressCreationDto;
import com.booking.domain.dtos.arena.ArenaCreationDto;
import com.booking.domain.dtos.arena.ArenaResultDto;
import com.booking.domain.dtos.arena.ArenaUpdateDto;
import com.booking.domain.dtos.arena.info.ArenaInfoCreationDto;
import com.booking.utils.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
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
public class ArenaControllerTest {

    private WebTestClient client;

    @Autowired
    private WebApplicationContext wac;

    private ArenaCreationDto arenaCreationDto;
    private ArenaUpdateDto arenaUpdateDto;
    private AddressCreationDto addressCreationDto;
    private ArenaInfoCreationDto arenaInfoCreationDto;

    @BeforeEach
    void setUp() {
        client = MockMvcWebTestClient.bindToApplicationContext(this.wac)
                .configureClient()
                .baseUrl("http://localhost:8081/api/arenas")
                .build();

        arenaCreationDto = new ArenaCreationDto();
        arenaInfoCreationDto = new ArenaInfoCreationDto();
        addressCreationDto = new AddressCreationDto();
        arenaInfoCreationDto.setAddress(addressCreationDto);
        arenaCreationDto.setName("arena");
        arenaCreationDto.setStatus(true);
        arenaCreationDto.setDescription("hala haala");
        arenaCreationDto.setArenaInfo(arenaInfoCreationDto);

        arenaUpdateDto = new ArenaUpdateDto();
    }

    @Test
    @Order(1)
    void createArena_ReturnStatusCreated() {
        try (MockedStatic<SecurityUtils> utilities = Mockito.mockStatic(SecurityUtils.class)) {
            utilities.when(SecurityUtils::getCurrentUserId).thenReturn(1L);
            client.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(arenaCreationDto), ArenaCreationDto.class)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody(ArenaResultDto.class);
        }
    }

    @Test
    @Order(2)
    void updateArena_ReturnStatusOk() {
        Long arenaId = 1L;
        client.put().uri("/" + arenaId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(arenaUpdateDto), ArenaUpdateDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ArenaResultDto.class);

    }

    @Test
    @Order(3)
    void getArenaById_ReturnStatusOk() {
        Long arenaId = 1L;
        client.get().uri("/" + arenaId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ArenaResultDto.class);
    }

    @Test
    @Order(4)
    void deleteArena_ReturnStatusNoContent() {
        Long arenaId = 1L;
        client.delete().uri("/" + arenaId)
                .exchange()
                .expectStatus().isNoContent();
    }
}
