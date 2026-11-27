package georisk.claimsservice.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import georisk.claimsservice.domain.enums.ClaimOrigin;
import georisk.claimsservice.domain.enums.ClaimStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "claim",
        uniqueConstraints = @UniqueConstraint(name = "uk_claim_alert_policy",
                columnNames = {"alert_id", "policy_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "expertise")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "claim_number", nullable = false, unique = true, length = 30)
    private String claimNumber;

    // Identifiants des autres microservices (pas de clé étrangère entre services)
    @NotNull
    @Column(name = "policy_id", nullable = false)
    private Long policyId;

    @Column(name = "proprietaire_id")
    private Long proprietaireId;

    @Column(name = "zone_id", length = 50)
    private String zoneId;

    @Enumerated(EnumType.STRING)
    @Column(name = "origin", nullable = false, length = 30)
    private ClaimOrigin origin;

    @Column(name = "alert_id", length = 50)
    private String alertId;

    @NotNull
    @Column(name = "incident_date", nullable = false)
    private LocalDate incidentDate;

    @PositiveOrZero
    @Column(name = "estimated_damage", precision = 15, scale = 2)
    private BigDecimal estimatedDamage;

    // Copies des valeurs de la police (MS2)
    @PositiveOrZero
    @Column(name = "coverage_limit", precision = 15, scale = 2)
    private BigDecimal coverageLimit;

    @PositiveOrZero
    @Column(name = "deductible", precision = 15, scale = 2)
    private BigDecimal deductible;

    @Column(name = "approved_amount", precision = 15, scale = 2)
    private BigDecimal approvedAmount;

    @Column(name = "paid_amount", precision = 15, scale = 2)
    private BigDecimal paidAmount;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "rejection_reason", length = 500)
    private String rejectionReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private ClaimStatus status;

    // Association Claim 1 <-> 0..1 Expertise (côté inverse)
    @OneToOne(mappedBy = "claim", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("claim")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Expertise expertise;
}