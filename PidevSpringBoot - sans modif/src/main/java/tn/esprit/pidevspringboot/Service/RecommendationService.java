package tn.esprit.pidevspringboot.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.esprit.pidevspringboot.Entities.Menu.Plat;
import tn.esprit.pidevspringboot.Entities.Menu.RecommendationResponseDTO;
import tn.esprit.pidevspringboot.Entities.MenuTrainingDTO;
import tn.esprit.pidevspringboot.Repository.MenuRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecommendationService {
    @Autowired
    private MenuRepository menuRepository;
    private final String PYTHON_SERVICE_URL = "http://localhost:5000/recommend"; // URL of the Flask service


    public List<RecommendationResponseDTO> recommendMenus(List<Long> menuIds) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Envelopper la liste dans un objet avec clé "menu_ids"
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("menu_ids", menuIds);
        System.out.println("Final request JSON: " + new ObjectMapper().writeValueAsString(requestBody));
        System.out.println("Sending menu_ids to Flask: " + menuIds);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<List<RecommendationResponseDTO>> response = restTemplate.exchange(
                PYTHON_SERVICE_URL,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<RecommendationResponseDTO>>() {}
        );

        return response.getBody();
    }

    public List<MenuTrainingDTO> exportTrainingData() {
        return menuRepository.findAll().stream().map(menu -> {
            MenuTrainingDTO dto = new MenuTrainingDTO();
            dto.setIdMenu(menu.getIdMenu());
            dto.setNomMenu(menu.getNomMenu());
            dto.setStatut(menu.getStatut().name());



            dto.setNutritionniste(
                    menu.getNutritionniste() != null
                            ? menu.getNutritionniste().getEmail()
                            : ""
            );
            List<String> plats = menu.getPlats().stream()
                    .map(Plat::getNomPlat)
                    .collect(Collectors.toList());
            dto.setPlats(plats);

            return dto;
        }).collect(Collectors.toList());
    }
}
