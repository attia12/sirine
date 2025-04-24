package tn.esprit.pidevspringboot.Entities.User;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "reclamation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reclamation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reclamation")
    private Integer idReclamation;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_reclamation", nullable = false)
    private Date dateReclamation;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_reclamation", nullable = false)
    private TypeReclamation typeReclamation;

    public enum TypeReclamation {
        Problème_Repas, Problème_Salle_Sport
    }

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutReclamation statut;

    public enum StatutReclamation {
        En_Cours, Résolue, Non_Résolue
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_resolution")
    private Date dateResolution;

    @Column(name = "action", columnDefinition = "TEXT")
    private String action;
}
