package tn.esprit.pidevspringboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevspringboot.Entities.Abonnement.Abonnement;

public interface AbonnementRepository extends JpaRepository<Abonnement, Long> {
}
