package tn.esprit.pidevspringboot.Entities.Menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

public class Plat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlat;

    private String nomPlat;

    @Enumerated(EnumType.STRING)
    private TypePlat typePlat;

    private Long calories;

    @Enumerated(EnumType.STRING)
    private TypeRegime regime;

    private String allergenes;

    @Enumerated(EnumType.STRING)
    private StatutPlat statutPlat;

    @ManyToMany(mappedBy = "plats")
    @JsonIgnore

    private List<Menu> menus = new ArrayList<>();

    // Getters and Setters

    public Long getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(Long idPlat) {
        this.idPlat = idPlat;
    }

    public String getNomPlat() {
        return nomPlat;
    }

    public void setNomPlat(String nomPlat) {
        this.nomPlat = nomPlat;
    }

    public TypePlat getTypePlat() {
        return typePlat;
    }

    public void setTypePlat(TypePlat typePlat) {
        this.typePlat = typePlat;
    }

    public Long getCalories() {
        return this.calories = calories;
    }

    public void setCalories(Long calories) {
        this.calories = calories;
    }

    public TypeRegime getRegime() {
        return regime;
    }

    public void setRegime(TypeRegime regime) {
        this.regime = regime;
    }

    public String getAllergenes() {
        return allergenes;
    }

    public void setAllergenes(String allergenes) {
        this.allergenes = allergenes;
    }

    public StatutPlat getStatutPlat() {
        return statutPlat;
    }

    public void setStatutPlat(StatutPlat statutPlat) {
        this.statutPlat = statutPlat;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
