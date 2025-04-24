package tn.esprit.pidevspringboot.Controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevspringboot.Entities.Abonnement.Abonnement;
import tn.esprit.pidevspringboot.Service.IAbonnementService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Abonnement")
@Tag(name = "Gestion des abonnements")
public class AbonnementController {

    @Autowired
    IAbonnementService iAbonnementService;

    @Operation(description = "Affichage de tous les abonnements")
    @GetMapping("/retrieveAllAbonnements")
    public List<Abonnement> afficherAbonnements() {
        return iAbonnementService.retrieveAllAbonnements();
    }

    @Operation(description = "Affichage d'un abonnement spécifique")
    @GetMapping("/retrieveAbonnement/{id}")
    public Abonnement afficherAbonnement(@PathVariable("id") Long id) {
        return iAbonnementService.retrieveAbonnement(id);
    }

    @Operation(description = "Ajouter un abonnement")
    @PostMapping("/addAbonnement")
    public Abonnement ajouterAbonnement(@RequestBody Abonnement abonnement) {
        return iAbonnementService.addAbonnement(abonnement);
    }



    @Operation(description = "Supprimer un abonnement")
    @DeleteMapping("/deleteAbonnement/{id}")
    public void deleteAbonnement(@PathVariable("id") Long id) {
        iAbonnementService.deleteAbonnement(id);
    }
}
