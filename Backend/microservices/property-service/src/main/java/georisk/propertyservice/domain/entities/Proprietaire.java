package georisk.propertyservice.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Proprietaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idProprietaire;

    @Column(unique = true) // nullable par défaut, tu ajouteras nullable = false avec Keycloak
    String keycloakUserId;

    String nom;
    String prenom;
    String email;
    String telephone;


    @OneToMany(mappedBy = "proprietaire")
    @JsonIgnore
    List<Propriete> proprietes = new ArrayList<>();

}