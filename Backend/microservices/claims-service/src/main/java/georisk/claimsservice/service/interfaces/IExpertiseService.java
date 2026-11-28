package georisk.claimsservice.service.interfaces;

import georisk.claimsservice.domain.entities.Expertise;

import java.util.List;

public interface IExpertiseService {

    List<Expertise> retrieveAllExpertises();

    Expertise retrieveExpertise(Long id);

    Expertise addExpertise(Long claimId, Expertise expertise);

    Expertise updateExpertise(Long id, Expertise expertise);

    void removeExpertise(Long id);
}