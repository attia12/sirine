package tn.esprit.pidevspringboot.Entities.Menu;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.pidevspringboot.Entities.User.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMenu;

    private String nomMenu;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    private StatutMenu statut;

    private Boolean confirme;

    @ManyToOne
    @JoinColumn(name = "id_nutritionniste")
    private User nutritionniste;

    @ManyToMany
    @JoinTable(
            name = "menu_plat",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "plat_id")
    )
    private List<Plat> plats = new ArrayList<>();
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();
    // Rename this to reflect that it is a list of likes
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MenuLike> menuLikes = new ArrayList<>();


    private Integer likesCount = 0;


    @PrePersist
    @PreUpdate
    public void updateLikesCount() {
        this.likesCount = this.menuLikes.size();
    }



}
