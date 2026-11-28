package tn.esprit.georisk.pricingservice.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.georisk.pricingservice.domain.entities.Quote;
import tn.esprit.georisk.pricingservice.service.interfaces.IQuoteService;

import java.util.List;

@RestController
@RequestMapping("/api/quotes")
@RequiredArgsConstructor
public class QuoteRestController {

    private final IQuoteService service;

    // GET /api/quotes  ou  GET /api/quotes?proprieteId=1
    @GetMapping
    public List<Quote> getAll(@RequestParam(required = false) Long proprieteId) {
        return proprieteId == null ? service.getAll() : service.getByPropriete(proprieteId);
    }

    @GetMapping("/{id}")
    public Quote getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Quote create(@RequestBody Quote quote) {
        return service.create(quote);
    }

    @PutMapping("/{id}")
    public Quote update(@PathVariable Long id, @RequestBody Quote quote) {
        return service.update(id, quote);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}