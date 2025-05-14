package co.edu.udistrital.mdp.caminatas.controllers.CaminatasControllers;

import co.edu.udistrital.mdp.caminatas.entities.CaminatasEntities.CaminataEntity;
import co.edu.udistrital.mdp.caminatas.services.CaminatasServices.CaminataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/caminatas")
public class CaminataController {

    @Autowired
    private CaminataService caminataService;

    @GetMapping
    public ResponseEntity<List<CaminataEntity>> getAll() {
        return ResponseEntity.ok(caminataService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaminataEntity> getById(@PathVariable Long id) {
        return caminataService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CaminataEntity> create(@RequestBody CaminataEntity caminata) {
        return ResponseEntity.status(201).body(caminataService.save(caminata));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CaminataEntity> update(@PathVariable Long id, @RequestBody CaminataEntity caminata) {
        return caminataService.findById(id)
                .map(c -> {
                    caminata.setId(id);
                    return ResponseEntity.ok(caminataService.save(caminata));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        caminataService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

