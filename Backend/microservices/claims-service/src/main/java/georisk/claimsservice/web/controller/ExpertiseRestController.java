package georisk.claimsservice.web.controller;

import georisk.claimsservice.domain.entities.Expertise;
import georisk.claimsservice.service.interfaces.IExpertiseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expertises")
@RequiredArgsConstructor
public class ExpertiseRestController {

    private final IExpertiseService expertiseService;

    @GetMapping
    public List<Expertise> getAllExpertises() {
        return expertiseService.retrieveAllExpertises();
    }

    @GetMapping("/{id}")
    public Expertise getExpertise(@PathVariable Long id) {
        return expertiseService.retrieveExpertise(id);
    }

    @PostMapping("/claim/{claimId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Expertise addExpertise(@PathVariable Long claimId,
                                  @Valid @RequestBody Expertise expertise) {
        return expertiseService.addExpertise(claimId, expertise);
    }

    @PutMapping("/{id}")
    public Expertise updateExpertise(@PathVariable Long id,
                                     @Valid @RequestBody Expertise expertise) {
        return expertiseService.updateExpertise(id, expertise);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpertise(@PathVariable Long id) {
        expertiseService.removeExpertise(id);
    }
}