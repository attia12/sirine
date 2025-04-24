package tn.esprit.pidevspringboot.Entities.Nutrition;

import jakarta.persistence.*;
import lombok.Data;
import tn.esprit.pidevspringboot.Entities.User.User;

import java.util.Date;

@Entity
@Data
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRendezVous;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateHeure;

    private int duree;
    private String remarque;
    private boolean rappelEnvoye;
}