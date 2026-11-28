package tn.esprit.georisk.pricingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.georisk.pricingservice.domain.entities.ZoneRiskCache;

@Repository
public interface ZoneRiskCacheRepository extends JpaRepository<ZoneRiskCache, String> {
}