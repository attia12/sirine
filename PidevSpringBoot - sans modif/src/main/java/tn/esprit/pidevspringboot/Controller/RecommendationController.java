package tn.esprit.pidevspringboot.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevspringboot.Entities.Menu.Menu;
import tn.esprit.pidevspringboot.Entities.Menu.MenuRequestDTO;
import tn.esprit.pidevspringboot.Entities.Menu.RecommendationResponseDTO;
import tn.esprit.pidevspringboot.Entities.MenuTrainingDTO;
import tn.esprit.pidevspringboot.Repository.MenuRepository;
import tn.esprit.pidevspringboot.Service.RecommendationService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recommendations")
@CrossOrigin("http://localhost:4200")
public class RecommendationController {
    @Autowired
    private RecommendationService recommendationService;
    @Autowired
    private MenuRepository menuRepository;

    // Route to handle incoming recommendation request
    @PostMapping("/recommend")
    public List<RecommendationResponseDTO> recommendMenus() throws JsonProcessingException {

        List<Menu> menus = menuRepository.findAll();

        // Extract all IDs
        List<Long> menuIds = menus.stream()
                .map(Menu::getIdMenu)
                .collect(Collectors.toList());

        // Shuffle and pick 3 random IDs (or fewer if not enough)
        Collections.shuffle(menuIds);
        List<Long> randomMenuIds = menuIds.stream()
                .limit(3)
                .collect(Collectors.toList());
        return recommendationService.recommendMenus(randomMenuIds);
    }
    @GetMapping("/training-data")
    public List<MenuTrainingDTO> getTrainingData() {
        return recommendationService.exportTrainingData();
    }
}
