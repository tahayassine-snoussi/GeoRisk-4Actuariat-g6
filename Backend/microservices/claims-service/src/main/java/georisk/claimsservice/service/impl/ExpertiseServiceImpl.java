package georisk.claimsservice.service.impl;

import georisk.claimsservice.domain.entities.Claim;
import georisk.claimsservice.domain.entities.Expertise;
import georisk.claimsservice.repository.ClaimRepository;
import georisk.claimsservice.repository.ExpertiseRepository;
import georisk.claimsservice.service.interfaces.IExpertiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertiseServiceImpl implements IExpertiseService {

    private final ExpertiseRepository expertiseRepository;
    private final ClaimRepository claimRepository;

    @Override
    public List<Expertise> retrieveAllExpertises() {
        return expertiseRepository.findAll();
    }

    @Override
    public Expertise retrieveExpertise(Long id) {
        return expertiseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Expertise " + id + " introuvable"));
    }

    @Override
    public Expertise addExpertise(Long claimId, Expertise expertise) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Claim " + claimId + " introuvable"));

        if (claim.getExpertise() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ce sinistre a déjà une expertise");
        }

        expertise.setId(null);
        expertise.setClaim(claim);     // côté propriétaire (clé étrangère)
        claim.setExpertise(expertise); // côté inverse
        return expertiseRepository.save(expertise);
    }

    @Override
    public Expertise updateExpertise(Long id, Expertise expertise) {
        Expertise existing = retrieveExpertise(id);

        // le sinistre lié n'est jamais modifié par un update
        existing.setExpertId(expertise.getExpertId());
        existing.setVisitDate(expertise.getVisitDate());
        existing.setCause(expertise.getCause());
        existing.setAssessedDamage(expertise.getAssessedDamage());
        existing.setFindings(expertise.getFindings());
        return expertiseRepository.save(existing);
    }

    @Override
    @Transactional
    public void removeExpertise(Long id) {
        Expertise expertise = retrieveExpertise(id);
        // on détache l'expertise du sinistre : orphanRemoval la supprime
        expertise.getClaim().setExpertise(null);
    }
}