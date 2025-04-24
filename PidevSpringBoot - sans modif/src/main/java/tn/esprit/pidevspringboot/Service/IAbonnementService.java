package tn.esprit.pidevspringboot.Service;
import tn.esprit.pidevspringboot.Entities.Abonnement.Abonnement;
import java.util.List;
import java.util.Optional;

public interface IAbonnementService {
    List<Abonnement> retrieveAllAbonnements();
    Abonnement retrieveAbonnement(Long id);
    Abonnement addAbonnement(Abonnement abonnement);
    void deleteAbonnement(Long id);
}
