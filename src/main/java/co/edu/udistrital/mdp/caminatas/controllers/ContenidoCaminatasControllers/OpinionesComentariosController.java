package co.edu.udistrital.mdp.caminatas.controllers.ContenidoCaminatasControllers;

import co.edu.udistrital.mdp.caminatas.dto.ContenidoCaminatasDTO.ComentarioDTO;
import co.edu.udistrital.mdp.caminatas.entities.CaminatasEntities.CaminataEntity;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.OpinionesComentariosEntity;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioNaturalEntity;
import co.edu.udistrital.mdp.caminatas.services.ContenidoCaminatasServices.OpinionesComentariosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Comentarios", description = "Comentarios y calificaciones sobre caminatas")
@RestController
@RequestMapping("/comentarios")
public class OpinionesComentariosController {

    @Autowired
    private OpinionesComentariosService opinion_ComentarioService;

    @Operation(summary = "Obtener una lista de todos los comentarios", description = "Obtiene una lista con todos los comentarios registrados")
    @GetMapping
    public ResponseEntity<List<OpinionesComentariosEntity>> getAll() {
        return ResponseEntity.ok(opinion_ComentarioService.findAll());
    }

    @Operation(summary = "Obtener un comentario por ID", description = "Obtiene un comentario{ID}")
    @GetMapping("/{id}")
    public ResponseEntity<OpinionesComentariosEntity> getById(@PathVariable Long id) {
        return opinion_ComentarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    /*
    @PostMapping
    public ResponseEntity<OpinionesComentariosEntity> create(@RequestBody OpinionesComentariosEntity comentario) {
        return ResponseEntity.status(201).body(opinion_ComentarioService.save(comentario));
    }
    */
    @Operation(summary = "Crear un comentario por ID", description = "Crea un comentario{ID}")
    @PostMapping
    public ResponseEntity<OpinionesComentariosEntity> create(@Valid @RequestBody ComentarioDTO dto) {
        OpinionesComentariosEntity comentario = new OpinionesComentariosEntity();

        // Recuperar entidades asociadas (simulado, o usar servicios si lo prefieres)
        UsuarioNaturalEntity autor = new UsuarioNaturalEntity();
        autor.setId(dto.getIdAutor());

        CaminataEntity caminata = new CaminataEntity();
        caminata.setId(dto.getIdCaminata());

        comentario.setAutor(autor);
        comentario.setCaminata(caminata);
        comentario.setDescripcionComentario(dto.getDescripcionComentario());
        comentario.setCalificacion(dto.getCalificacion());

        return ResponseEntity.status(201).body(opinion_ComentarioService.save(comentario));
    }
    /*
    @PutMapping("/{id}")
    public ResponseEntity<OpinionesComentariosEntity> update(@PathVariable Long id, @RequestBody OpinionesComentariosEntity comentario) {
        return opinion_ComentarioService.findById(id)
                .map(c -> {
                    comentario.setId(id);
                    return ResponseEntity.ok(opinion_ComentarioService.save(comentario));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    */
    @Operation(summary = "Actualizar un comentario por ID", description = "Actualiza un comentario{ID}")
    @PutMapping("/{id}")
    public ResponseEntity<OpinionesComentariosEntity> update(@PathVariable Long id, @Valid @RequestBody ComentarioDTO dto) {
        return opinion_ComentarioService.findById(id)
            .map(existing -> {
                UsuarioNaturalEntity autor = new UsuarioNaturalEntity();
                autor.setId(dto.getIdAutor());

                CaminataEntity caminata = new CaminataEntity();
                caminata.setId(dto.getIdCaminata());

                existing.setDescripcionComentario(dto.getDescripcionComentario());
                existing.setCalificacion(dto.getCalificacion());
                existing.setAutor(autor);
                existing.setCaminata(caminata);

                return ResponseEntity.ok(opinion_ComentarioService.save(existing));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un comentario por ID", description = "Elimina un comentario{ID}")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        opinion_ComentarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
