package co.edu.udistrital.mdp.caminatas.controllers.ContenidoCaminatasControllers;

import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.ContenidoCaminatasDTO.ComentarioDTO;
import co.edu.udistrital.mdp.caminatas.entities.CaminatasEntities.CaminataEntity;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.ComentariosEntity;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioNaturalEntity;
import co.edu.udistrital.mdp.caminatas.services.ContenidoCaminatasServices.ComentarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Comentarios", description = "Comentarios y calificaciones sobre caminatas")
@RestController
@RequestMapping("/comentarios")
public class ComentariosController {

    private final ComentarioService comentarioService;

    // Constructor para inyección de dependencias
    public ComentariosController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @Operation(summary = "Crear un comentario por ID", description = "Crea un comentario{ID}")
    @PostMapping
    public ResponseEntity<ComentariosEntity> create(@Valid @RequestBody ComentarioDTO dto) {
        ComentariosEntity comentario = new ComentariosEntity();

        // Recuperar entidades asociadas (simulado, o usar servicios si lo prefieres)
        UsuarioNaturalEntity autor = new UsuarioNaturalEntity();
        autor.setId(dto.getIdAutor());

        CaminataEntity caminata = new CaminataEntity();
        caminata.setId(dto.getIdCaminata());

        comentario.setAutor(autor);
        comentario.setCaminata(caminata);
        comentario.setDescripcionComentario(dto.getDescripcionComentario());
        comentario.setCalificacion(dto.getCalificacion());

        return ResponseEntity.status(201).body(comentarioService.save(comentario));
    }

    @Operation(summary = "Obtener una lista de todos los comentarios", description = "Obtiene una lista con todos los comentarios registrados")
    @GetMapping
    public ResponseEntity<List<ComentariosEntity>> getAll() {
        return ResponseEntity.ok(comentarioService.findAll());
    }

    @Operation(summary = "Obtener un comentario por ID", description = "Obtiene un comentario{ID}")
    @GetMapping("/{id}")
    public ResponseEntity<ComentariosEntity> getById(@PathVariable Long id) {
        return comentarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar un comentario por ID", description = "Actualiza un comentario{ID}")
    @PutMapping("/{id}")
    public ResponseEntity<ComentariosEntity> update(@PathVariable Long id, @Valid @RequestBody ComentarioDTO dto) {
        return comentarioService.findById(id)
            .map(existing -> {
                UsuarioNaturalEntity autor = new UsuarioNaturalEntity();
                autor.setId(dto.getIdAutor());

                CaminataEntity caminata = new CaminataEntity();
                caminata.setId(dto.getIdCaminata());

                existing.setDescripcionComentario(dto.getDescripcionComentario());
                existing.setCalificacion(dto.getCalificacion());
                existing.setAutor(autor);
                existing.setCaminata(caminata);

                return ResponseEntity.ok(comentarioService.save(existing));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un comentario por ID", description = "Elimina un comentario{ID}")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        comentarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    
}
