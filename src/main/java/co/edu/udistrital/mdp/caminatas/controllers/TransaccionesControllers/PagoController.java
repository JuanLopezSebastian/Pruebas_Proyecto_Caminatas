package co.edu.udistrital.mdp.caminatas.controllers.TransaccionesControllers;

import co.edu.udistrital.mdp.caminatas.dto.TransaccionesDTO.PagoDTO;
import co.edu.udistrital.mdp.caminatas.entities.TransaccionesEntities.PagoEntity;
import co.edu.udistrital.mdp.caminatas.services.TransaccionesServices.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pagos", description = "Registro de pagos realizados por usuarios")
@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Operation(summary = "Obtener una lista de pagos", description = "Obtiene todos los pagos registrados")
    @GetMapping
    public ResponseEntity<List<PagoEntity>> getAll() {
        return ResponseEntity.ok(pagoService.findAll());
    }

    @Operation(summary = "Obtener un pago por ID", description = "Obtiene un pago{ID}")
    @GetMapping("/{id}")
    public ResponseEntity<PagoEntity> getById(@PathVariable Long id) {
        return pagoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    /*
    @PostMapping
    public ResponseEntity<PagoEntity> create(@RequestBody PagoEntity pago) {
        return ResponseEntity.status(201).body(pagoService.save(pago));
    }
    */
    @Operation(summary = "Crear un pago por ID", description = "Crea o registra el estado de un pago{ID}")
    @PostMapping
    public ResponseEntity<PagoEntity> create(@Valid @RequestBody PagoDTO dto) {
        PagoEntity pago = new PagoEntity();
        pago.setIdPago(dto.getIdPago());

        return ResponseEntity.status(201).body(pagoService.save(pago));
    }
    /*
    @PutMapping("/{id}")
    public ResponseEntity<PagoEntity> update(@PathVariable Long id, @RequestBody PagoEntity pago) {
        return pagoService.findById(id)
                .map(p -> {
                    pago.setId(id);
                    return ResponseEntity.ok(pagoService.save(pago));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    */
    @Operation(summary = "Actualizar un pago por ID", description = "Actualiza un pago{ID}")
    @PutMapping("/{id}")
    public ResponseEntity<PagoEntity> update(@PathVariable Long id, @Valid @RequestBody PagoDTO dto) {
        return pagoService.findById(id)
                .map(existing -> {
                    existing.setIdPago(dto.getIdPago());
                    return ResponseEntity.ok(pagoService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un pago por ID", description = "Eliminar un pago{ID}")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pagoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

