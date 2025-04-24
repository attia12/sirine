package tn.esprit.pidevspringboot.Entities.Menu;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class MenuRequestDTO {
    private List<Long> menu_ids;
}
