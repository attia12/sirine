package tn.esprit.pidevspringboot.Entities.ActiviteSportive;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Seance_sport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seance")
    private Long id_seance;


    @Temporal(TemporalType.TIME)
    private Date heureDebut;

    @Temporal(TemporalType.TIME)
    private Date heureFin;

    @ManyToOne
    Activite activite;

    @OneToMany(mappedBy = "seance_sport")
    Set<Reservation> reservations;
}
