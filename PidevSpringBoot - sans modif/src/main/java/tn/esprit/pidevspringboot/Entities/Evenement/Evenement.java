package tn.esprit.pidevspringboot.Entities.Evenement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "evenement")
public class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEvenement;

    @Column(nullable = false, length = 255)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime dateEvenement;

    @Column(nullable = false, length = 255)
    private String lieu;

    @Column(nullable = true)
    private Integer capaciteMax;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeEvenement typeEvenement; // Relation ENUM 1:1 avec TypeEvenement

    @OneToMany(mappedBy = "evenement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InscriptionEvenement> inscriptions; // Relation 1:N avec InscriptionEvenement
}
