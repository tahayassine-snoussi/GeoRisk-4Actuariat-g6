package georisk.claimsservice.repository;

import georisk.claimsservice.domain.entities.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    boolean existsByClaimNumber(String claimNumber);

    boolean existsByAlertIdAndPolicyId(String alertId, Long policyId);
}