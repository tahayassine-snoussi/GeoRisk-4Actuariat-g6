package georisk.propertyservice.service.impl;

import georisk.propertyservice.domain.entities.Propriete;
import georisk.propertyservice.domain.enums.StatutPropriete;
import georisk.propertyservice.repository.ProprieteRepository;
import georisk.propertyservice.service.interfaces.IProprieteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProprieteServiceImpl implements IProprieteService {

    private final ProprieteRepository repository;

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
    @Transactional
    public Propriete create(Propriete propriete) {
        propriete.setIdPropriete(null);   // l'id est généré par la base
        return repository.save(propriete);
    }

    @Override
    @Transactional
    public Propriete update(Long id, Propriete data) {
        Propriete existing = getById(id);   // 404 si absent
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
        existing.setStatut(data.getStatut());
        // quoteId volontairement non modifiable ici : il est lié au devis du pricing-service
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
}