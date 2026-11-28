package tn.esprit.georisk.pricingservice.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.georisk.pricingservice.domain.entities.ZoneRiskCache;
import tn.esprit.georisk.pricingservice.service.interfaces.IZoneRiskCacheService;

import java.util.List;

@RestController
@RequestMapping("/api/zone-scores")
@RequiredArgsConstructor
public class ZoneRiskCacheRestController {

    private final IZoneRiskCacheService service;

    @GetMapping
    public List<ZoneRiskCache> getAll() {
        return service.getAll();
    }

    @GetMapping("/{zoneId}")
    public ZoneRiskCache getById(@PathVariable String zoneId) {
        return service.getById(zoneId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ZoneRiskCache create(@RequestBody ZoneRiskCache cache) {
        return service.create(cache);
    }

    @PutMapping("/{zoneId}")
    public ZoneRiskCache update(@PathVariable String zoneId, @RequestBody ZoneRiskCache cache) {
        return service.update(zoneId, cache.getScore());
    }

    @DeleteMapping("/{zoneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String zoneId) {
        service.delete(zoneId);
    }
}