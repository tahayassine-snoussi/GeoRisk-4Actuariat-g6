package georisk.propertyservice.domain.entities;


import georisk.propertyservice.domain.enums.StatutPropriete;
import georisk.propertyservice.domain.enums.TypeLogement;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Propriete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPropriete;

    String adresse;
    String ville;
    double latitude;
    double longitude;
    String zoneId;

    @Enumerated(EnumType.STRING)
    TypeLogement type;

    double surfaceM2;
    Integer anneeConstruction;
    Integer etage;
    BigDecimal valeur;
    Long quoteId;

    @Enumerated(EnumType.STRING)
    StatutPropriete statut;

    @ManyToOne
    Proprietaire proprietaire;

}
