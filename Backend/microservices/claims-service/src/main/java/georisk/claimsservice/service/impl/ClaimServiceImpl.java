package georisk.claimsservice.service.impl;

import georisk.claimsservice.domain.entities.Claim;
import georisk.claimsservice.domain.enums.ClaimOrigin;
import georisk.claimsservice.domain.enums.ClaimStatus;
import georisk.claimsservice.repository.ClaimRepository;
import georisk.claimsservice.service.interfaces.IClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Year;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClaimServiceImpl implements IClaimService {

    private final ClaimRepository claimRepository;

    @Override
    public List<Claim> retrieveAllClaims() {
        return claimRepository.findAll();
    }

    @Override
    public Claim retrieveClaim(Long id) {
        return claimRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Claim " + id + " introuvable"));
    }

    @Override
    public Claim addClaim(Claim claim) {
        claim.setId(null);

        if (claim.getClaimNumber() == null || claim.getClaimNumber().isBlank()) {
            claim.setClaimNumber(generateClaimNumber());
        } else if (claimRepository.existsByClaimNumber(claim.getClaimNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Le numéro " + claim.getClaimNumber() + " existe déjà");
        }

        // Un seul pré-sinistre par couple (alerte, police)
        if (claim.getAlertId() != null
                && claimRepository.existsByAlertIdAndPolicyId(claim.getAlertId(), claim.getPolicyId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Un sinistre existe déjà pour cette alerte et cette police");
        }

        if (claim.getOrigin() == null) {
            claim.setOrigin(claim.getAlertId() != null
                    ? ClaimOrigin.ALERT_PREOPEN
                    : ClaimOrigin.OWNER_DECLARATION);
        }
        if (claim.getStatus() == null) {
            claim.setStatus(claim.getOrigin() == ClaimOrigin.ALERT_PREOPEN
                    ? ClaimStatus.PRE_OPENED
                    : ClaimStatus.DECLARED);
        }
        return claimRepository.save(claim);
    }

    @Override
    public Claim updateClaim(Long id, Claim claim) {
        Claim existing = retrieveClaim(id);

        // claimNumber et expertise ne sont jamais modifiés par un update
        existing.setPolicyId(claim.getPolicyId());
        existing.setProprietaireId(claim.getProprietaireId());
        existing.setZoneId(claim.getZoneId());
        existing.setAlertId(claim.getAlertId());
        existing.setIncidentDate(claim.getIncidentDate());
        existing.setEstimatedDamage(claim.getEstimatedDamage());
        existing.setCoverageLimit(claim.getCoverageLimit());
        existing.setDeductible(claim.getDeductible());
        existing.setApprovedAmount(claim.getApprovedAmount());
        existing.setPaidAmount(claim.getPaidAmount());
        existing.setPaidAt(claim.getPaidAt());
        existing.setRejectionReason(claim.getRejectionReason());
        if (claim.getOrigin() != null) {
            existing.setOrigin(claim.getOrigin());
        }
        if (claim.getStatus() != null) {
            existing.setStatus(claim.getStatus());
        }
        return claimRepository.save(existing);
    }

    @Override
    public void removeClaim(Long id) {
        // la cascade (CascadeType.ALL) supprime aussi l'expertise liée
        claimRepository.delete(retrieveClaim(id));
    }

    private String generateClaimNumber() {
        String number;
        do {
            number = "CLM-" + Year.now().getValue() + "-"
                    + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (claimRepository.existsByClaimNumber(number));
        return number;
    }
}