package tn.esprit.georisk.pricingservice.service.interfaces;

import tn.esprit.georisk.pricingservice.domain.entities.ZoneRiskCache;

import java.util.List;

public interface IZoneRiskCacheService {
    List<ZoneRiskCache> getAll();
    ZoneRiskCache getById(String zoneId);
    ZoneRiskCache create(ZoneRiskCache cache);
    ZoneRiskCache update(String zoneId, int score);
    void delete(String zoneId);
}