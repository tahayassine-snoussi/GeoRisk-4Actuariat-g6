package tn.esprit.georisk.pricingservice.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/* Copie locale du score de risque d'une zone, fourni par MS5 (risk-engine).
Sert de repli quand MS5 est indisponible.
 */
@Entity
@Table(name = "zone_risk_cache")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZoneRiskCache {

    @Id
    private String zoneId;          // identifiant de la zone chez MS5 : pas de génération automatique

    @Column(nullable = false)
    private int score;              // de 0 à 100

    private LocalDateTime updatedAt;
}