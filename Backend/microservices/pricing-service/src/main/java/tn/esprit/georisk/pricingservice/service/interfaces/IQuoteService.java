package tn.esprit.georisk.pricingservice.service.interfaces;

import tn.esprit.georisk.pricingservice.domain.entities.Quote;

import java.util.List;

public interface IQuoteService {
    List<Quote> getAll();
    Quote getById(Long id);
    List<Quote> getByPropriete(Long proprieteId);
    Quote create(Quote quote);
    Quote update(Long id, Quote quote);
    void delete(Long id);
}