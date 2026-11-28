package georisk.propertyservice.service.impl;

import georisk.propertyservice.domain.entities.Proprietaire;
import georisk.propertyservice.repository.ProprietaireRepository;
import georisk.propertyservice.service.interfaces.IProprietaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProprietaireServiceImpl implements IProprietaireService {

    private final ProprietaireRepository repository;

    @Override
    public List<Proprietaire> getAll() {
        return repository.findAll();
    }

    @Override
    public Proprietaire getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Propriétaire " + id + " introuvable"));
    }

    @Override
    public Proprietaire getByKeycloakUserId(String keycloakUserId) {
        return repository.findByKeycloakUserId(keycloakUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Propriétaire introuvable pour l'utilisateur Keycloak " + keycloakUserId));
    }

    @Override
    @Transactional
    public Proprietaire create(Proprietaire proprietaire) {
        proprietaire.setIdProprietaire(null);
        return repository.save(proprietaire);
    }

    @Override
    @Transactional
    public Proprietaire update(Long id, Proprietaire data) {
        Proprietaire existing = getById(id);
        existing.setNom(data.getNom());
        existing.setPrenom(data.getPrenom());
        existing.setEmail(data.getEmail());
        existing.setTelephone(data.getTelephone());
        // keycloakUserId non modifiable : c'est le lien avec le compte Keycloak
        return repository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.delete(getById(id));
    }
}