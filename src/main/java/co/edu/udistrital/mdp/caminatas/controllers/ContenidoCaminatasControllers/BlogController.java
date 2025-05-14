package co.edu.udistrital.mdp.caminatas.controllers.ContenidoCaminatasControllers;

import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.BlogEntity;
import co.edu.udistrital.mdp.caminatas.services.ContenidoCaminatasServices.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping
    public ResponseEntity<List<BlogEntity>> getAll() {
        return ResponseEntity.ok(blogService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogEntity> getById(@PathVariable Long id) {
        return blogService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BlogEntity> create(@RequestBody BlogEntity blog) {
        return ResponseEntity.status(201).body(blogService.save(blog));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogEntity> update(@PathVariable Long id, @RequestBody BlogEntity blog) {
        return blogService.findById(id)
                .map(b -> {
                    blog.setId(id);
                    return ResponseEntity.ok(blogService.save(blog));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        blogService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
