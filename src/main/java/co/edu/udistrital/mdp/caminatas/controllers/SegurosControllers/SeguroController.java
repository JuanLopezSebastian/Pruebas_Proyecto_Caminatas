package co.edu.udistrital.mdp.caminatas.controllers.SegurosControllers;

import co.edu.udistrital.mdp.caminatas.entities.SegurosEntities.SeguroEntity;
import co.edu.udistrital.mdp.caminatas.services.SegurosServices.SeguroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seguros")
public class SeguroController {

    @Autowired
    private SeguroService seguroService;

    @GetMapping
    public ResponseEntity<List<SeguroEntity>> getAll() {
        return ResponseEntity.ok(seguroService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeguroEntity> getById(@PathVariable Long id) {
        return seguroService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SeguroEntity> create(@RequestBody SeguroEntity seguro) {
        return ResponseEntity.status(201).body(seguroService.save(seguro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeguroEntity> update(@PathVariable Long id, @RequestBody SeguroEntity seguro) {
        return seguroService.findById(id)
                .map(s -> {
                    seguro.setId(id);
                    return ResponseEntity.ok(seguroService.save(seguro));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        seguroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
