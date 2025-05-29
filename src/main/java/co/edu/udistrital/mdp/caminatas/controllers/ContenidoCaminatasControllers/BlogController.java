package co.edu.udistrital.mdp.caminatas.controllers.ContenidoCaminatasControllers;

import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.ContenidoCaminatasDTO.BlogDTO;
import co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.ContenidoCaminatasDTO.BlogResponseDTO;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.BlogEntity;
import co.edu.udistrital.mdp.caminatas.services.ContenidoCaminatasServices.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Blogs", description = "Gestión de blogs relacionados con caminatas")
@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @Operation(summary = "Crear un nuevo blog")
    @PostMapping
    public ResponseEntity<BlogResponseDTO> create(@Valid @RequestBody BlogDTO dto) {
        BlogEntity creado = blogService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(blogService.toResponseDTO(creado));
    }

    @Operation(summary = "Obtener todos los blogs")
    @GetMapping
    public ResponseEntity<List<BlogResponseDTO>> getAll() {
        List<BlogResponseDTO> list = blogService.findAll().stream()
            .map(blogService::toResponseDTO)
            .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Obtener un blog por ID")
    @GetMapping("/{id}")
    public ResponseEntity<BlogResponseDTO> getById(@PathVariable Long id) {
        return blogService.findById(id)
            .map(blogService::toResponseDTO)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar un blog por ID")
    @PutMapping("/{id}")
    public ResponseEntity<BlogEntity> update(@PathVariable Long id, @Valid @RequestBody BlogDTO dto) {
        return blogService.findById(id)
            .map(existing -> {
                // Sobrescribe (para simplificación rápida; podrías hacer uno específico luego)
                existing.setTitle(dto.getTitle());
                existing.setContent(dto.getContent());
                existing.setContenidoTextoBlog(dto.getContenidoTextoBlog());
                existing.setImagenes(dto.getImagenes());
                existing.setVideos(dto.getVideos());
                return ResponseEntity.ok(blogService.save(dto));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un blog por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        blogService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
