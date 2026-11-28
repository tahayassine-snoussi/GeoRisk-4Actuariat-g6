package georisk.propertyservice.domain.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

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
}