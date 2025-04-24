package tn.esprit.pidevspringboot.Service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidevspringboot.Entities.Abonnement.Abonnement;
import tn.esprit.pidevspringboot.Repository.AbonnementRepository;

import java.util.List;


@Service
@AllArgsConstructor
public class AbonnementServiceImpl implements IAbonnementService {
    @Autowired
    AbonnementRepository abonnementRepository;

    @Override
    public List<Abonnement> retrieveAllAbonnements() {
        return abonnementRepository.findAll();
    }

    @Override
    public Abonnement retrieveAbonnement(Long id) {
        return abonnementRepository.findById(id).orElse(null);
    }

    @Override
    public Abonnement addAbonnement(Abonnement abonnement) {
        return abonnementRepository.save(abonnement);
    }



    @Override
    public void deleteAbonnement(Long id) {
        abonnementRepository.deleteById(id);
    }
}

