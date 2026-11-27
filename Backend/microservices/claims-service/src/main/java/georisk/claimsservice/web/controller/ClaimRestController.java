package georisk.claimsservice.web.controller;

import georisk.claimsservice.domain.entities.Claim;
import georisk.claimsservice.service.interfaces.IClaimService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
public class ClaimRestController {

    private final IClaimService claimService;

    @GetMapping
    public List<Claim> getAllClaims() {
        return claimService.retrieveAllClaims();
    }

    @GetMapping("/{id}")
    public Claim getClaim(@PathVariable Long id) {
        return claimService.retrieveClaim(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Claim addClaim(@Valid @RequestBody Claim claim) {
        return claimService.addClaim(claim);
    }

    @PutMapping("/{id}")
    public Claim updateClaim(@PathVariable Long id, @Valid @RequestBody Claim claim) {
        return claimService.updateClaim(id, claim);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClaim(@PathVariable Long id) {
        claimService.removeClaim(id);
    }
}