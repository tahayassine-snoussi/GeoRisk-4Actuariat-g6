package georisk.claimsservice.service.interfaces;

import georisk.claimsservice.domain.entities.Claim;

import java.util.List;

public interface IClaimService {

    List<Claim> retrieveAllClaims();

    Claim retrieveClaim(Long id);

    Claim addClaim(Claim claim);

    Claim updateClaim(Long id, Claim claim);

    void removeClaim(Long id);
}