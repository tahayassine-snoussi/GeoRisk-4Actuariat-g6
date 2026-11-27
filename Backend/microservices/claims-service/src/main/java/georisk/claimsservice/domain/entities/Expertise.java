package georisk.claimsservice.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import georisk.claimsservice.domain.enums.DamageCause;
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

@Entity
@Table(name = "expertise")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "claim")
public class Expertise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expert_id", length = 50)
    private String expertId;

    @Column(name = "visit_date")
    private LocalDate visitDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cause", nullable = false, length = 20)
    private DamageCause cause;

    @PositiveOrZero
    @Column(name = "assessed_damage", precision = 15, scale = 2)
    private BigDecimal assessedDamage;

    @Column(name = "findings", length = 2000)
    private String findings;

    // Association Claim 1 <-> 0..1 Expertise (côté propriétaire : porte la clé étrangère claim_id)
    @OneToOne
    @JoinColumn(name = "claim_id", nullable = false, unique = true)
    @JsonIgnoreProperties("expertise")
    private Claim claim;
}