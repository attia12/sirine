package tn.esprit.pidevspringboot.Entities.ActiviteSportive;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.pidevspringboot.Entities.User.User;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Activite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_activite;

    @Column(nullable = false)
    private String nomActivite;

    private String description;

    @ManyToMany(mappedBy = "activite")
    Set<User>user;
    @OneToMany(mappedBy = "activite")
    Set<Seance_sport> seance_sports;
}
