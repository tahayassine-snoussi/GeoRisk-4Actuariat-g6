package tn.esprit.georisk.pricingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.georisk.pricingservice.domain.entities.Quote;
import tn.esprit.georisk.pricingservice.domain.enums.QuoteStatus;
import tn.esprit.georisk.pricingservice.repository.QuoteRepository;
import tn.esprit.georisk.pricingservice.service.interfaces.IQuoteService;
import tn.esprit.georisk.pricingservice.domain.entities.ZoneRiskCache;
import tn.esprit.georisk.pricingservice.service.interfaces.IZoneRiskCacheService;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements IQuoteService {

    private final QuoteRepository repository;
    private final IZoneRiskCacheService zoneService;
    @Override
    public List<Quote> getAll() {
        return repository.findAll();
    }

    @Override
    public Quote getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Devis " + id + " introuvable"));
    }

    @Override
    public List<Quote> getByPropriete(Long proprieteId) {
        return repository.findByProprieteId(proprieteId);
    }

    @Override
    public Quote create(Quote quote) {
        quote.setId(null);                                   // l'id est généré par la base
        quote.setStatus(QuoteStatus.VALID);                  // un nouveau devis est toujours VALID
        quote.setValidUntil(LocalDate.now().plusDays(30));   // valable 30 jours
        ZoneRiskCache zone = zoneService.getById(quote.getZone().getZoneId()); // 404 si la zone n'existe pas
        quote.setZone(zone);
        quote.setZoneScore(zone.getScore());   // score figé au moment du devis
        return repository.save(quote);
    }

    @Override
    public Quote update(Long id, Quote quote) {
        Quote existing = getById(id);                        // 404 si absent
        if (existing.getStatus() != QuoteStatus.VALID) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Le devis " + id + " n'est plus modifiable (" + existing.getStatus() + ")");
        }
        existing.setProprieteId(quote.getProprieteId());
        ZoneRiskCache zone = zoneService.getById(quote.getZone().getZoneId());
        existing.setZone(zone);
        existing.setZoneScore(zone.getScore());
        existing.setInsuredValue(quote.getInsuredValue());
        existing.setDeductible(quote.getDeductible());
        existing.setPurePremium(quote.getPurePremium());
        existing.setAnnualPremium(quote.getAnnualPremium());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Quote existing = getById(id);
        if (existing.getStatus() == QuoteStatus.ACCEPTED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Un devis accepté est lié à une police : suppression interdite");
        }
        repository.delete(existing);
    }
}