package co.edu.udistrital.mdp.caminatas.controllers.TransaccionesControllers;

import co.edu.udistrital.mdp.caminatas.entities.TransaccionesEntities.InscripcionUsuarioEntity;
import co.edu.udistrital.mdp.caminatas.services.TransaccionesServices.InscripcionUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionUsuarioController {

    @Autowired
    private InscripcionUsuarioService inscripcionUsuarioService;

    @GetMapping
    public ResponseEntity<List<InscripcionUsuarioEntity>> getAll() {
        return ResponseEntity.ok(inscripcionUsuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscripcionUsuarioEntity> getById(@PathVariable Long id) {
        return inscripcionUsuarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InscripcionUsuarioEntity> create(@RequestBody InscripcionUsuarioEntity inscripcion) {
        return ResponseEntity.status(201).body(inscripcionUsuarioService.save(inscripcion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inscripcionUsuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

