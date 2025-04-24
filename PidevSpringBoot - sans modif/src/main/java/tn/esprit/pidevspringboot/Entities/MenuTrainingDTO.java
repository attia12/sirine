package tn.esprit.pidevspringboot.Entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuTrainingDTO {
    private Long idMenu;
    private String nomMenu;
    private String statut;
    private String nutritionniste;
    private List<String> plats;
}
