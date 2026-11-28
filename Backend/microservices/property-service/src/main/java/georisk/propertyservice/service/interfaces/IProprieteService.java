package georisk.propertyservice.service.interfaces;

import georisk.propertyservice.domain.entities.Propriete;
import georisk.propertyservice.domain.enums.StatutPropriete;

import java.util.List;

public interface IProprieteService {
    List<Propriete> getAll();
    Propriete getById(Long id);
    List<Propriete> getByVille(String ville);
    List<Propriete> getByStatut(StatutPropriete statut);
    List<Propriete> getByProprietaire(Long idProprietaire);
    Propriete create(Propriete propriete);
    Propriete update(Long id, Propriete propriete);
    void delete(Long id);
}