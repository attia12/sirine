package tn.esprit.pidevspringboot.Entities.ActiviteSportive;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_reservation;

    @Enumerated (EnumType.STRING)
    Status status;

    @Temporal(TemporalType.DATE)
    private Date dateReservation;

    @ManyToOne
    Seance_sport seance_sport;
}

