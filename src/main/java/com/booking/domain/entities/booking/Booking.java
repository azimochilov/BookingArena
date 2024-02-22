package com.booking.domain.entities.booking;
import com.booking.domain.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

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
    @ManyToOne
    @JoinColumn(name = "reservation_arena_id", referencedColumnName = "id")
    private ReservationArena reservationArena;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void setBooking(ReservationArena reservationArena) {
        this.reservationArena = reservationArena;
        reservationArena.getBooking().add(this);
    }

    public void setUser(User user) {
        this.user = user;
        reservationArena.getBooking().add(this);
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getConsumer() {
        return consumer;
    }
}
