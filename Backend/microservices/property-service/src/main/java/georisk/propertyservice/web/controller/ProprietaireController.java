package georisk.propertyservice.web.controller;

import georisk.propertyservice.domain.entities.Proprietaire;
import georisk.propertyservice.service.interfaces.IProprietaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proprietaires")
@RequiredArgsConstructor
public class ProprietaireController {

    private final IProprietaireService service;

    @GetMapping
    public List<Proprietaire> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Proprietaire getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/keycloak/{keycloakUserId}")
    public Proprietaire getByKeycloakUserId(@PathVariable String keycloakUserId) {
        return service.getByKeycloakUserId(keycloakUserId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Proprietaire create(@RequestBody Proprietaire proprietaire) {
        return service.create(proprietaire);
    }

    @PutMapping("/{id}")
    public Proprietaire update(@PathVariable Long id, @RequestBody Proprietaire proprietaire) {
        return service.update(id, proprietaire);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}