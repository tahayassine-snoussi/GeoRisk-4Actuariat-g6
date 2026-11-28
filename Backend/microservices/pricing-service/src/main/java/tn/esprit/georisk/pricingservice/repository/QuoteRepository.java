package tn.esprit.georisk.pricingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.georisk.pricingservice.domain.entities.Quote;
import tn.esprit.georisk.pricingservice.domain.enums.QuoteStatus;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    // les devis d'une propriété : GET /api/quotes?proprieteId=1
    List<Quote> findByProprieteId(Long proprieteId);

    // les devis encore VALID dont la date est dépassée (pour les passer à EXPIRED)
    List<Quote> findByStatusAndValidUntilBefore(QuoteStatus status, LocalDate date);

    boolean existsByZoneZoneId(String zoneId);   // une zone est-elle utilisée par un devis ?
}