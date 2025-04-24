package tn.esprit.pidevspringboot.Service;

import org.springframework.transaction.annotation.Transactional;
import tn.esprit.pidevspringboot.Entities.Menu.Plat;

import java.util.List;

public interface IPlatService {

    @Transactional
    Plat addPlat(Plat plat, List<Long> menuIds);  // Seule version nécessaire

    Plat updatePlat(Long idPlat, Plat plat);

    void deletePlat(Long idPlat);

    List<Plat> getAllPlats();

    Plat getPlatById(Long idPlat);

    List<Plat> getPlatsByAllergene(String allergene);
}