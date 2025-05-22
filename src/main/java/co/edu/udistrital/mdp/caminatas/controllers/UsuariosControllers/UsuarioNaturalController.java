package co.edu.udistrital.mdp.caminatas.controllers.UsuariosControllers;

import java.util.List;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import co.edu.udistrital.mdp.caminatas.dto.UsuariosDTO.UsuarioNaturalDTO;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioNaturalEntity;
import co.edu.udistrital.mdp.caminatas.services.UsuariosServices.UsuarioNaturalService;

@Tag(name = "Usuarios Naturales", description = "Operaciones relacionadas con usuarios tipo natural")
@RestController
@RequestMapping("/usuarios/naturales")
public class UsuarioNaturalController {

    @Autowired
    private UsuarioNaturalService usuarioService;

    @Operation(summary = "Obtener una lista de los usuarios naturales", description = "Obtiene todos los usuarios naturales registrados")
    @GetMapping
    public ResponseEntity<List<UsuarioNaturalEntity>> getAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @Operation(summary = "Obtener un usuario natural por ID", description = "Obtiene un usuario{ID}")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioNaturalEntity> getById(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /*
    @PostMapping
    public ResponseEntity<UsuarioNaturalEntity> create(@RequestBody UsuarioNaturalEntity usuario) {
        return ResponseEntity.status(201).body(usuarioService.save(usuario));
    }
    */
    @Operation(summary = "Crear un usuario natural", description = "Registra un nuevo usuario tipo NATURAL en el sistema.")
    @PostMapping
    public ResponseEntity<UsuarioNaturalEntity> create(@Valid @RequestBody UsuarioNaturalDTO dto) {
        UsuarioNaturalEntity usuario = new UsuarioNaturalEntity();
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setTipoUsuario(dto.getTipoUsuario());
        usuario.setCorreo(dto.getCorreo());
        usuario.setCedula(dto.getCedula());
        usuario.setTelefono(dto.getTelefono());

        return ResponseEntity.status(201).body(usuarioService.save(usuario));
    }
    /*
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioNaturalEntity> update(@PathVariable Long id, @RequestBody UsuarioNaturalEntity usuario) {
        return usuarioService.findById(id)
                .map(u -> {
                    usuario.setId(id);
                    return ResponseEntity.ok(usuarioService.save(usuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    */

    @Operation(summary = "Actualiza info de un usuario natural por ID", description = "Actualiza info de un usuario{ID}")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioNaturalEntity> update(@PathVariable Long id, @Valid @RequestBody UsuarioNaturalDTO dto) {
        return usuarioService.findById(id)
            .map(existing -> {
                existing.setNombreUsuario(dto.getNombreUsuario());
                existing.setTipoUsuario(dto.getTipoUsuario());
                existing.setCorreo(dto.getCorreo());
                existing.setCedula(dto.getCedula());
                existing.setTelefono(dto.getTelefono());

                return ResponseEntity.ok(usuarioService.save(existing));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Elimina un usuario natural por ID", description = "Elimina un usuario{ID}")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }


}

