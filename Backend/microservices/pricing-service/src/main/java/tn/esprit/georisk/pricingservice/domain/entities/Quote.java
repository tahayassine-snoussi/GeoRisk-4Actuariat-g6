package tn.esprit.georisk.pricingservice.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.georisk.pricingservice.domain.enums.QuoteStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Devis calculé pour une propriété de MS1.
 * proprieteId et zoneId sont de simples identifiants : pas de clé étrangère entre services.
 * zoneScore est une copie du score utilisé, pour que le prix ne change pas si le score évolue.
 */
@Entity
@Table(name = "quote")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long proprieteId;

    @Column(nullable = false)
    private String zoneId;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal insuredValue;    // valeur assurée (TND)

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal deductible;      // franchise (TND)

    @Column(nullable = false)
    private int zoneScore;              // score copié au moment du calcul

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal purePremium;     // prime pure

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal annualPremium;   // prime annuelle (avec frais, marge, taxe)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuoteStatus status;

    private LocalDate validUntil;       // date de création + 30 jours
}