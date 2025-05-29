package co.edu.udistrital.mdp.caminatas.controllers.SegurosControllers;

import co.edu.udistrital.mdp.caminatas.entities.SegurosEntities.SeguroEntity;
import co.edu.udistrital.mdp.caminatas.services.SegurosServices.SeguroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Seguros", description = "Gestión de seguros para caminatas")
@RestController
@RequestMapping("/seguros")
public class SeguroController {

    private final SeguroService seguroService;

    // Constructor para inyección de dependencias
    public SeguroController(SeguroService seguroService) {
        this.seguroService = seguroService;
    }

    @Operation(summary = "Crear una factura por ID", description = "Crea una factura{ID}")
    @PostMapping
    public ResponseEntity<SeguroEntity> create(@RequestBody SeguroEntity seguro) {
        return ResponseEntity.status(201).body(seguroService.save(seguro));
    }

    @Operation(summary = "Obtener una lista de seguros", description = "Obtiene una lista de todos los seguros registrados")
    @GetMapping
    public ResponseEntity<List<SeguroEntity>> getAll() {
        return ResponseEntity.ok(seguroService.findAll());
    }

    @Operation(summary = "Obtener un seguro por ID", description = "Obtiene un seguro{ID}")
    @GetMapping("/{id}")
    public ResponseEntity<SeguroEntity> getById(@PathVariable Long id) {
        return seguroService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar una factura por ID", description = "Actualizar una factura{ID}")
    @PutMapping("/{id}")
    public ResponseEntity<SeguroEntity> update(@PathVariable Long id, @RequestBody SeguroEntity seguro) {
        return seguroService.findById(id)
                .map(s -> {
                    seguro.setId(id);
                    return ResponseEntity.ok(seguroService.save(seguro));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar una factura por ID", description = "Elimina una factura{ID}")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        seguroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
