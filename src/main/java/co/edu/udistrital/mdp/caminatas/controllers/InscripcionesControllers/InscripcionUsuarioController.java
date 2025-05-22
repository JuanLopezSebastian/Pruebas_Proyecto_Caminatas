package co.edu.udistrital.mdp.caminatas.controllers.InscripcionesControllers;

import co.edu.udistrital.mdp.caminatas.dto.InscripcionesDTO.InscripcionUsuarioDTO;
import co.edu.udistrital.mdp.caminatas.entities.CaminatasEntities.CaminataEntity;
import co.edu.udistrital.mdp.caminatas.entities.InscripcionesEntities.InscripcionUsuarioEntity;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioNaturalEntity;
import co.edu.udistrital.mdp.caminatas.services.InscripcionesServices.InscripcionUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Inscripciones", description = "Manejo de inscripciones de usuarios a caminatas")
@RestController
@RequestMapping("/inscripciones")
public class InscripcionUsuarioController {

    @Autowired
    private InscripcionUsuarioService inscripcionUsuarioService;

    @Operation(summary = "Obtener una lista de inscripciones", description = "Obtiene una lista de todas las inscripciones")
    @GetMapping
    public ResponseEntity<List<InscripcionUsuarioEntity>> getAll() {
        return ResponseEntity.ok(inscripcionUsuarioService.findAll());
    }

    @Operation(summary = "Obtener un usuario natural por ID", description = "Obtiene un usuario{ID}")
    @GetMapping("/{id}")
    public ResponseEntity<InscripcionUsuarioEntity> getById(@PathVariable Long id) {
        return inscripcionUsuarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    /*
    @PostMapping
    public ResponseEntity<InscripcionUsuarioEntity> create(@RequestBody InscripcionUsuarioEntity inscripcion) {
        return ResponseEntity.status(201).body(inscripcionUsuarioService.save(inscripcion));
    }
    */
    @Operation(summary = "Obtener una inscripción por ID", description = "Obtiene una inscripcion{ID}")
    @PostMapping
    public ResponseEntity<InscripcionUsuarioEntity> create(@Valid @RequestBody InscripcionUsuarioDTO dto) {
        InscripcionUsuarioEntity inscripcion = new InscripcionUsuarioEntity();

        UsuarioNaturalEntity usuario = new UsuarioNaturalEntity();
        usuario.setId(dto.getIdUsuario());

        CaminataEntity caminata = new CaminataEntity();
        caminata.setId(dto.getIdCaminata());

        inscripcion.setUsuario(usuario);
        inscripcion.setCaminata(caminata);
        inscripcion.setFechaInscripcion(dto.getFechaInscripcion());
        inscripcion.setEstadoPago(dto.getEstadoPago());

        return ResponseEntity.status(201).body(inscripcionUsuarioService.save(inscripcion));
    }

    @Operation(summary = "Actualizar una inscripción por ID", description = "Actualizar una inscripcion{ID}")
    @PutMapping("/{id}")
    public ResponseEntity<InscripcionUsuarioEntity> update(@PathVariable Long id, @Valid @RequestBody InscripcionUsuarioDTO dto) {
        return inscripcionUsuarioService.findById(id)
            .map(existing -> {
                UsuarioNaturalEntity usuario = new UsuarioNaturalEntity();
                usuario.setId(dto.getIdUsuario());

                CaminataEntity caminata = new CaminataEntity();
                caminata.setId(dto.getIdCaminata());

                existing.setFechaInscripcion(dto.getFechaInscripcion());
                existing.setEstadoPago(dto.getEstadoPago());
                existing.setUsuario(usuario);
                existing.setCaminata(caminata);

                return ResponseEntity.ok(inscripcionUsuarioService.save(existing));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar una inscripción por ID", description = "Elimina una inscripción{ID}")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inscripcionUsuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

