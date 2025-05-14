package co.edu.udistrital.mdp.caminatas.controllers.UsuariosControllers;

import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioNaturalEntity;
import co.edu.udistrital.mdp.caminatas.services.UsuariosServices.UsuarioNaturalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios/naturales")
public class UsuarioNaturalController {

    @Autowired
    private UsuarioNaturalService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioNaturalEntity>> getAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioNaturalEntity> getById(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioNaturalEntity> create(@RequestBody UsuarioNaturalEntity usuario) {
        return ResponseEntity.status(201).body(usuarioService.save(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioNaturalEntity> update(@PathVariable Long id, @RequestBody UsuarioNaturalEntity usuario) {
        return usuarioService.findById(id)
                .map(u -> {
                    usuario.setId(id);
                    return ResponseEntity.ok(usuarioService.save(usuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

