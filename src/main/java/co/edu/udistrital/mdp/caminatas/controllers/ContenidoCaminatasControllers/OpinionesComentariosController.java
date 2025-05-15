package co.edu.udistrital.mdp.caminatas.controllers.ContenidoCaminatasControllers;

import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.OpinionesComentariosEntity;
import co.edu.udistrital.mdp.caminatas.services.ContenidoCaminatasServices.OpinionesComentariosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import co.edu.udistrital.mdp.caminatas.repositories.TransaccionesRepositories.I_InscripcionUsuarioRepository;

@RestController
@RequestMapping("/api/comentarios")
public class OpinionesComentariosController {

    @Autowired
    private OpinionesComentariosService opinion_ComentarioService;

    @GetMapping
    public ResponseEntity<List<OpinionesComentariosEntity>> getAll() {
        return ResponseEntity.ok(opinion_ComentarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpinionesComentariosEntity> getById(@PathVariable Long id) {
        return opinion_ComentarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OpinionesComentariosEntity> create(@RequestBody OpinionesComentariosEntity comentario) {
        return ResponseEntity.status(201).body(opinion_ComentarioService.save(comentario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OpinionesComentariosEntity> update(@PathVariable Long id, @RequestBody OpinionesComentariosEntity comentario) {
        return opinion_ComentarioService.findById(id)
                .map(c -> {
                    comentario.setId(id);
                    return ResponseEntity.ok(opinion_ComentarioService.save(comentario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        opinion_ComentarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
