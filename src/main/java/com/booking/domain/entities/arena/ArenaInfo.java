package com.booking.domain.entities.arena;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "arena_info")
public class ArenaInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "phone")
    private String phone;
    @Column(name = "price")
    private Integer price;
    @Column(name = "worked_from")
    private Instant workedFrom;
    @Column(name = "worked_to")
    private Instant workedTo;
    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "updated_at")
    private Instant updatedAt;
    @Column(name = "is_booked")
    private boolean isBooked;
    @Column(name = "street")

    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @OneToOne
    @JoinColumn(name = "arena_id", referencedColumnName = "id")
    private Arena arena;
}
