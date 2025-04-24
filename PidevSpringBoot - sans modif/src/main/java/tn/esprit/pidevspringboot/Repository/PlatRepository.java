package tn.esprit.pidevspringboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevspringboot.Entities.Menu.Plat;
import tn.esprit.pidevspringboot.Entities.Menu.StatutPlat;

import java.util.List;
/*public interface PlatRepository extends JpaRepository<Plat, Long> {
}
*/
public interface PlatRepository extends JpaRepository<Plat, Long> {
    List<Plat> findByAllergenesContaining(String allergene);
    //List<Plat> findByStatutPlat(StatutPlat statutPlat);
}
