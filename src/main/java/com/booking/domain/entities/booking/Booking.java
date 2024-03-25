package com.booking.domain.entities.booking;
import com.booking.domain.entities.arena.Arena;
import com.booking.domain.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "consumer")
    private String consumer;

    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "booking_from")
    private Instant bookingFrom;
    @Column(name = "booking_to")
    private Instant bookingTo;
    @Column(name = "description")
    private String description;
    @Column(name = "total_price")
    private Integer totalPrice;
    @Column(name = "costumer")
    private String costumer;

    @ManyToOne
    @JoinColumn(name = "arena_id", referencedColumnName = "id")
    private Arena arena;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getConsumer() {
        return consumer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getBookingFrom() {
        return bookingFrom;
    }

    public void setBookingFrom(Instant bookingFrom) {
        this.bookingFrom = bookingFrom;
    }

    public Instant getBookingTo() {
        return bookingTo;
    }

    public void setBookingTo(Instant bookingTo) {
        this.bookingTo = bookingTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCostumer() {
        return costumer;
    }

    public void setCostumer(String costumer) {
        this.costumer = costumer;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
