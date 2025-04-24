package tn.esprit.pidevspringboot.Entities.Menu;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class RecommendationResponseDTO {
    private Long idMenu;
    private String nomMenu;
    private String statut;
    private List<String> plats;
    private String nutritionniste; // even if it's null
    private String features;


}
