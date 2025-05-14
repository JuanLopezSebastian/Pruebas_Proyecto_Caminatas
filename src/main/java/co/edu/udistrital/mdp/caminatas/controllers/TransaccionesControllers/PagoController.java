package co.edu.udistrital.mdp.caminatas.controllers.TransaccionesControllers;

import co.edu.udistrital.mdp.caminatas.entities.TransaccionesEntities.PagoEntity;
import co.edu.udistrital.mdp.caminatas.services.TransaccionesServices.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<PagoEntity>> getAll() {
        return ResponseEntity.ok(pagoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoEntity> getById(@PathVariable Long id) {
        return pagoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PagoEntity> create(@RequestBody PagoEntity pago) {
        return ResponseEntity.status(201).body(pagoService.save(pago));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoEntity> update(@PathVariable Long id, @RequestBody PagoEntity pago) {
        return pagoService.findById(id)
                .map(p -> {
                    pago.setId(id);
                    return ResponseEntity.ok(pagoService.save(pago));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pagoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

