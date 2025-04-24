package tn.esprit.pidevspringboot.Entities.Nutrition;

import jakarta.persistence.*;
import lombok.Data;
import tn.esprit.pidevspringboot.Entities.User.User;

@Entity
@Data
public class DossierMedical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDossier;

    @OneToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    private String maladies;
    private String allergies;
    private String objectifSante;
    private String traitements;
    private float imc;
    private float tailles;
    private float poids;

    @Enumerated(EnumType.STRING)
    private GroupSanguin groupeSanguin;
}