package tn.esprit.pidevspringboot.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.pidevspringboot.Entities.Menu.Menu;
import tn.esprit.pidevspringboot.Entities.Menu.Plat;
import tn.esprit.pidevspringboot.Entities.Menu.TypePlat;
import tn.esprit.pidevspringboot.Entities.Menu.StatutPlat;
import tn.esprit.pidevspringboot.Repository.MenuRepository;
import tn.esprit.pidevspringboot.Repository.PlatRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlatService implements IPlatService {

    private final PlatRepository platRepository;
    private final MenuRepository menuRepository;

    @Transactional
    @Override
    public Plat addPlat(Plat plat, List<Long> menuIds) {
        if (plat.getNomPlat() == null || plat.getNomPlat().isEmpty()) {
            throw new IllegalArgumentException("Le nom du plat est obligatoire");
        }
        if (plat.getCalories() <= 0) {
            throw new IllegalArgumentException("Les calories doivent être supérieures à zéro");
        }

        if (menuIds == null || menuIds.isEmpty()) {
            throw new IllegalArgumentException("La liste des menus ne peut pas être vide");
        }

        if (plat.getTypePlat() == null) {
            plat.setTypePlat(TypePlat.PLAT_PRINCIPAL);
        }
        if (plat.getStatutPlat() == null) {
            plat.setStatutPlat(StatutPlat.CONFIRME);
        }

        // Sauvegarde du plat
        Plat savedPlat = platRepository.save(plat);

        // Ajouter le plat aux menus spécifiés
        for (Long idMenu : menuIds) {
            Menu menu = menuRepository.findById(idMenu)
                    .orElseThrow(() -> new EntityNotFoundException("Menu introuvable pour l'ID " + idMenu));
            menu.getPlats().add(savedPlat);  // Ajouter le plat à la liste des plats du menu
            plat.getMenus().add(menu);  // Ajouter le menu à la liste des menus du plat
            menuRepository.save(menu);  // Sauvegarder le menu après ajout
        }

        return savedPlat;
    }

    @Override
    @Transactional
    public Plat updatePlat(Long idPlat, Plat updatedPlat) {
        Plat existingPlat = platRepository.findById(idPlat)
                .orElseThrow(() -> new EntityNotFoundException("Plat introuvable pour l'ID " + idPlat));

        existingPlat.setNomPlat(updatedPlat.getNomPlat());
        existingPlat.setTypePlat(updatedPlat.getTypePlat());
        existingPlat.setCalories(updatedPlat.getCalories());
        existingPlat.setRegime(updatedPlat.getRegime());
        existingPlat.setAllergenes(updatedPlat.getAllergenes());
        existingPlat.setStatutPlat(updatedPlat.getStatutPlat());

        return platRepository.save(existingPlat);
    }

    @Override
    @Transactional
    public void deletePlat(Long idPlat) {
        Plat plat = platRepository.findById(idPlat)
                .orElseThrow(() -> new EntityNotFoundException("Plat introuvable pour l'ID " + idPlat));

        // Retirer le plat de tous les menus auxquels il est associé
        for (Menu menu : plat.getMenus()) {
            menu.getPlats().remove(plat);
        }

        platRepository.delete(plat);
    }

    @Override
    public List<Plat> getAllPlats() {
        return platRepository.findAll();
    }

    @Override
    public Plat getPlatById(Long idPlat) {
        return platRepository.findById(idPlat)
                .orElseThrow(() -> new EntityNotFoundException("Plat introuvable pour l'ID " + idPlat));
    }

    @Override
    public List<Plat> getPlatsByAllergene(String allergene) {
        return platRepository.findByAllergenesContaining(allergene);
    }
}
