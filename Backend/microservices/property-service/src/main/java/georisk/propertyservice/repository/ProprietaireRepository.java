package georisk.propertyservice.repository;

import georisk.propertyservice.domain.entities.Proprietaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProprietaireRepository extends JpaRepository<Proprietaire, Long> {

    Optional<Proprietaire> findByKeycloakUserId(String keycloakUserId);
}