package com.booking.domain.entities.arena;

import com.booking.domain.entities.booking.ReservationArena;
import com.booking.domain.entities.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "arena")
public class Arena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "status", columnDefinition = "boolean default true")
    private boolean status = true;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(mappedBy = "arena", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private ArenaInfo arenaInfo;

    @Builder.Default
    @OneToMany(mappedBy = "arena", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<ReservationArena> reservationArena = new ArrayList<>();
}
