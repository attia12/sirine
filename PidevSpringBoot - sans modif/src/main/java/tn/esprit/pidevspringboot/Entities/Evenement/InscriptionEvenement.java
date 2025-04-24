package tn.esprit.pidevspringboot.Entities.Evenement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.pidevspringboot.Entities.User.User;


import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inscription_evenement")
public class InscriptionEvenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInscription;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user; // Relation 1:N avec User

    @ManyToOne
    @JoinColumn(name = "id_evenement", nullable = false)
    private Evenement evenement; // Relation 1:N avec Evenement

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateInscription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutInscription statutInscription; // Relation ENUM 1:1 avec StatutInscription
}
