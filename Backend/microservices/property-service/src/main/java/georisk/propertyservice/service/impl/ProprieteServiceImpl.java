package georisk.propertyservice.service.impl;

import georisk.propertyservice.domain.entities.Proprietaire;
import georisk.propertyservice.domain.entities.Propriete;
import georisk.propertyservice.domain.enums.StatutPropriete;
import georisk.propertyservice.repository.ProprietaireRepository;
import georisk.propertyservice.repository.ProprieteRepository;
import georisk.propertyservice.service.interfaces.IProprieteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProprieteServiceImpl implements IProprieteService {

    private static final BigDecimal VALEUR_MIN = new BigDecimal("20000");
    private static final BigDecimal VALEUR_MAX = new BigDecimal("2000000");

    private final ProprieteRepository repository;
    private final ProprietaireRepository proprietaireRepository;

    @Override
    public List<Propriete> getAll() {
        return repository.findAll();
    }

    @Override
    public Propriete getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Propriété " + id + " introuvable"));
    }

    @Override
    public List<Propriete> getByVille(String ville) {
        return repository.findByVille(ville);
    }

    @Override
    public List<Propriete> getByStatut(StatutPropriete statut) {
        return repository.findByStatut(statut);
    }

    @Override
    public List<Propriete> getByProprietaire(Long idProprietaire) {
        return repository.findByProprietaire_IdProprietaire(idProprietaire);
    }

    @Override
    @Transactional
    public Propriete create(Propriete propriete) {
        verifierValeur(propriete.getValeur());

        if (propriete.getProprietaire() == null
                || propriete.getProprietaire().getIdProprietaire() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Le propriétaire est obligatoire");
        }
        Long idProprietaire = propriete.getProprietaire().getIdProprietaire();
        Proprietaire proprietaire = proprietaireRepository.findById(idProprietaire)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Propriétaire " + idProprietaire + " introuvable"));

        propriete.setIdPropriete(null);
        propriete.setProprietaire(proprietaire);
        propriete.setQuoteId(null);
        propriete.setStatut(StatutPropriete.ENREGISTREE);
        return repository.save(propriete);
    }

    @Override
    @Transactional
    public Propriete update(Long id, Propriete data) {
        verifierValeur(data.getValeur());

        Propriete existing = getById(id);
        existing.setAdresse(data.getAdresse());
        existing.setVille(data.getVille());
        existing.setLatitude(data.getLatitude());
        existing.setLongitude(data.getLongitude());
        existing.setZoneId(data.getZoneId());
        existing.setType(data.getType());
        existing.setSurfaceM2(data.getSurfaceM2());
        existing.setAnneeConstruction(data.getAnneeConstruction());
        existing.setEtage(data.getEtage());
        existing.setValeur(data.getValeur());
        // proprietaire, quoteId et statut non modifiables ici
        return repository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Propriete existing = getById(id);
        if (existing.getQuoteId() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "La propriété " + id + " est liée au devis " + existing.getQuoteId()
                            + " : suppression interdite");
        }
        repository.delete(existing);
    }

    private void verifierValeur(BigDecimal valeur) {
        if (valeur == null || valeur.compareTo(VALEUR_MIN) < 0 || valeur.compareTo(VALEUR_MAX) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La valeur doit être comprise entre 20 000 et 2 000 000 TND");
        }
    }
}