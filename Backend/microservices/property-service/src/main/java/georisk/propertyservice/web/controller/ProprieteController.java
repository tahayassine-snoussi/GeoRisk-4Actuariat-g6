package georisk.propertyservice.web.controller;

import georisk.propertyservice.domain.entities.Propriete;
import georisk.propertyservice.domain.enums.StatutPropriete;
import georisk.propertyservice.service.interfaces.IProprieteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proprietes")
@RequiredArgsConstructor
public class ProprieteController {

    private final IProprieteService service;

    @GetMapping
    public List<Propriete> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Propriete getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/ville/{ville}")
    public List<Propriete> getByVille(@PathVariable String ville) {
        return service.getByVille(ville);
    }

    @GetMapping("/statut/{statut}")
    public List<Propriete> getByStatut(@PathVariable StatutPropriete statut) {
        return service.getByStatut(statut);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Propriete create(@RequestBody Propriete propriete) {
        return service.create(propriete);
    }

    @PutMapping("/{id}")
    public Propriete update(@PathVariable Long id, @RequestBody Propriete propriete) {
        return service.update(id, propriete);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}