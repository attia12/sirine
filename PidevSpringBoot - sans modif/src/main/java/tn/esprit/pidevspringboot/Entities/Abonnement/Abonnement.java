package tn.esprit.pidevspringboot.Entities.Abonnement;
import jakarta.persistence.*;
import lombok.Data;
import tn.esprit.pidevspringboot.Entities.User.User;
import java.util.Date;
@Entity
@Data


public class Abonnement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAbonnement;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Enumerated(EnumType.STRING)
    private TypeAbonnement typeAbonnement;

    @Enumerated(EnumType.STRING)
    private DureeAbonnement dureeAbonnement;

    private Date dateCreation;
    private Date dateFin;

    @Enumerated(EnumType.STRING)
    private StatutAbonnement statut;

    private float montant;
    private String modePaiement;

    public Long getIdAbonnement() {
        return idAbonnement;
    }

    public void setIdAbonnement(Long idAbonnement) {
        this.idAbonnement = idAbonnement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public DureeAbonnement getDureeAbonnement() {
        return dureeAbonnement;
    }

    public void setDureeAbonnement(DureeAbonnement dureeAbonnement) {
        this.dureeAbonnement = dureeAbonnement;
    }

    public TypeAbonnement getTypeAbonnement() {
        return typeAbonnement;
    }

    public void setTypeAbonnement(TypeAbonnement typeAbonnement) {
        this.typeAbonnement = typeAbonnement;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public StatutAbonnement getStatut() {
        return statut;
    }

    public void setStatut(StatutAbonnement statut) {
        this.statut = statut;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }
}
