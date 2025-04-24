package tn.esprit.pidevspringboot.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevspringboot.Entities.Menu.Plat;
import tn.esprit.pidevspringboot.Entities.Menu.StatutPlat;
import tn.esprit.pidevspringboot.Entities.Menu.TypePlat;
import tn.esprit.pidevspringboot.Entities.Menu.TypeRegime;
import tn.esprit.pidevspringboot.Service.IPlatService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plat")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Gestion des plats")
public class PlatController {

    private final IPlatService platService;

    @PostMapping("/add")
    public Plat addPlat(@RequestBody Map<String, Object> platRequest) {
        // Extraire les informations du JSON
        Plat plat = new Plat();

        // Safely cast the menuIds to List<Long>
        List<Long> menuIds = new ArrayList<>();
        List<?> menuIdsRaw = (List<?>) platRequest.get("menuIds");

        // If menuIdsRaw is not null, convert each element to Long
        if (menuIdsRaw != null) {
            for (Object id : menuIdsRaw) {
                if (id instanceof Integer) {
                    menuIds.add(((Integer) id).longValue());  // Convert Integer to Long
                } else if (id instanceof Long) {
                    menuIds.add((Long) id);
                }
            }
        }


        plat.setNomPlat((String) platRequest.get("nomPlat"));
        plat.setCalories(((Number) platRequest.get("calories")).longValue());  // Ensure calories is Long
        plat.setRegime(TypeRegime.valueOf((String) platRequest.get("regime")));
        plat.setAllergenes((String) platRequest.get("allergenes"));
        plat.setStatutPlat(StatutPlat.valueOf((String) platRequest.get("statutPlat")));
        plat.setTypePlat(TypePlat.valueOf((String) platRequest.get("typePlat")));


        return platService.addPlat(plat, menuIds);
    }

    @PutMapping("/update/{idPlat}")
    public Plat updatePlat(@PathVariable Long idPlat, @RequestBody Plat plat) {
        return platService.updatePlat(idPlat, plat);
    }

    @DeleteMapping("/delete/{idPlat}")
    public void deletePlat(@PathVariable Long idPlat) {
        platService.deletePlat(idPlat);
    }

    @GetMapping("/all")
    public List<Plat> getAllPlats() {
        return platService.getAllPlats();
    }

    @GetMapping("/get/{idPlat}")
    public Plat getPlatById(@PathVariable Long idPlat) {
        return platService.getPlatById(idPlat);
    }

    @GetMapping("/allergene/{allergene}")
    public List<Plat> getPlatsByAllergene(@PathVariable String allergene) {
        return platService.getPlatsByAllergene(allergene);
    }
}
