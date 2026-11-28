package georisk.propertyservice.service.interfaces;

import georisk.propertyservice.domain.entities.Proprietaire;

import java.util.List;

public interface IProprietaireService {
    List<Proprietaire> getAll();
    Proprietaire getById(Long id);
    Proprietaire getByKeycloakUserId(String keycloakUserId);
    Proprietaire create(Proprietaire proprietaire);
    Proprietaire update(Long id, Proprietaire proprietaire);
    void delete(Long id);
}