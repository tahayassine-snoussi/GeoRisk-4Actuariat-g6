package tn.esprit.georisk.pricingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.georisk.pricingservice.domain.entities.ZoneRiskCache;
import tn.esprit.georisk.pricingservice.repository.ZoneRiskCacheRepository;
import tn.esprit.georisk.pricingservice.service.interfaces.IZoneRiskCacheService;
import tn.esprit.georisk.pricingservice.repository.QuoteRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor   // Lombok : crée le constructeur qui injecte le repository
public class ZoneRiskCacheServiceImpl implements IZoneRiskCacheService {

    private final ZoneRiskCacheRepository repository;
    private final QuoteRepository quoteRepository;

    @Override
    public List<ZoneRiskCache> getAll() {
        return repository.findAll();
    }

    @Override
    public ZoneRiskCache getById(String zoneId) {
        return repository.findById(zoneId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Aucun score pour la zone " + zoneId));
    }

    @Override
    public ZoneRiskCache create(ZoneRiskCache cache) {
        if (repository.existsById(cache.getZoneId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "La zone " + cache.getZoneId() + " existe déjà");
        }
        checkScore(cache.getScore());
        cache.setUpdatedAt(LocalDateTime.now());
        return repository.save(cache);
    }

    @Override
    public ZoneRiskCache update(String zoneId, int score) {
        ZoneRiskCache cache = getById(zoneId);        // 404 si absente
        checkScore(score);
        cache.setScore(score);
        cache.setUpdatedAt(LocalDateTime.now());
        return repository.save(cache);
    }

    @Override
    public void delete(String zoneId) {
        getById(zoneId);                                   // 404 si absente
        if (quoteRepository.existsByZoneZoneId(zoneId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "La zone " + zoneId + " est utilisée par des devis");
        }
        repository.deleteById(zoneId);
    }

    private void checkScore(int score) {
        if (score < 0 || score > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Le score doit être entre 0 et 100");
        }
    }
}