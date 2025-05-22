package co.edu.udistrital.mdp.caminatas.controllers.ContenidoCaminatasControllers;

import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.BlogEntity;
import co.edu.udistrital.mdp.caminatas.services.ContenidoCaminatasServices.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Blogs", description = "Gestión de blogs relacionados con caminatas")
@RestController
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Operation(summary = "Obtener una lista de todos los blogs", description = "Obtiene una lista de todos los blogs registrados")
    @GetMapping
    public ResponseEntity<List<BlogEntity>> getAll() {
        return ResponseEntity.ok(blogService.findAll());
    }

    @Operation(summary = "Obtener un blog por ID", description = "Obtiene un blog{ID}")
    @GetMapping("/{id}")
    public ResponseEntity<BlogEntity> getById(@PathVariable Long id) {
        return blogService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un blog por ID", description = "Crea un blog{ID}")
    @PostMapping
    public ResponseEntity<BlogEntity> create(@RequestBody BlogEntity blog) {
       return ResponseEntity.status(201).body(blogService.save(blog));
    }

    @Operation(summary = "Actualizar un blog por ID", description = "Actualizar un blog{ID}")
    @PutMapping("/{id}")
    public ResponseEntity<BlogEntity> update(@PathVariable Long id, @RequestBody BlogEntity blog) {
        return blogService.findById(id)
                .map(b -> {
                    blog.setId(id);
                    return ResponseEntity.ok(blogService.save(blog));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un blog por ID", description = "Elimina un blog{ID}")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        blogService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
