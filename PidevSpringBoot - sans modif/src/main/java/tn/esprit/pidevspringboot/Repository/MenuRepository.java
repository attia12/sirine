package tn.esprit.pidevspringboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevspringboot.Entities.Menu.Menu;
import tn.esprit.pidevspringboot.Entities.Menu.StatutMenu;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByStatut(StatutMenu statut);

    List<Menu> findByConfirme(boolean b);

    List<Menu> findByNomMenuContainingIgnoreCaseAndConfirmeTrue(String query);
}
