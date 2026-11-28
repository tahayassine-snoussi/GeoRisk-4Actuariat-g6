package georisk.propertyservice.repository;

import georisk.propertyservice.domain.entities.Propriete;
import georisk.propertyservice.domain.enums.StatutPropriete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProprieteRepository extends JpaRepository<Propriete, Long> {

    List<Propriete> findByVille(String ville);

    List<Propriete> findByStatut(StatutPropriete statut);

    List<Propriete> findByProprietaire_IdProprietaire(Long idProprietaire);

    boolean existsByProprietaire_IdProprietaire(Long idProprietaire);
}