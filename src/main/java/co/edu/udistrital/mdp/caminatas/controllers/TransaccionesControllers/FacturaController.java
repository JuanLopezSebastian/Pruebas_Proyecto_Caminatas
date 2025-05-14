package co.edu.udistrital.mdp.caminatas.controllers.TransaccionesControllers;

import co.edu.udistrital.mdp.caminatas.entities.TransaccionesEntities.FacturaEntity;
import co.edu.udistrital.mdp.caminatas.services.TransaccionesServices.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public ResponseEntity<List<FacturaEntity>> getAll() {
        return ResponseEntity.ok(facturaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaEntity> getById(@PathVariable Long id) {
        return facturaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FacturaEntity> create(@RequestBody FacturaEntity factura) {
        return ResponseEntity.status(201).body(facturaService.save(factura));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacturaEntity> update(@PathVariable Long id, @RequestBody FacturaEntity factura) {
        return facturaService.findById(id)
                .map(f -> {
                    factura.setId(id);
                    return ResponseEntity.ok(facturaService.save(factura));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facturaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

