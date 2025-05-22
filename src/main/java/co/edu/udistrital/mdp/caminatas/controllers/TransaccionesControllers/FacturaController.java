package co.edu.udistrital.mdp.caminatas.controllers.TransaccionesControllers;

import co.edu.udistrital.mdp.caminatas.dto.TransaccionesDTO.FacturaDTO;
import co.edu.udistrital.mdp.caminatas.entities.TransaccionesEntities.FacturaEntity;
import co.edu.udistrital.mdp.caminatas.entities.TransaccionesEntities.PagoEntity;
import co.edu.udistrital.mdp.caminatas.services.TransaccionesServices.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Facturas", description = "Emisión de facturas asociadas a pagos")
@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Operation(summary = "Obtener una lista de facturas", description = "Obtiene una lista de todas las facturas")
    @GetMapping
    public ResponseEntity<List<FacturaEntity>> getAll() {
        return ResponseEntity.ok(facturaService.findAll());
    }

    @Operation(summary = "Obtener una factura por ID", description = "Obtiene una factura{ID}")
    @GetMapping("/{id}")
    public ResponseEntity<FacturaEntity> getById(@PathVariable Long id) {
        return facturaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    /*
    @PostMapping
    public ResponseEntity<FacturaEntity> create(@RequestBody FacturaEntity factura) {
        return ResponseEntity.status(201).body(facturaService.save(factura));
    }
    */
    @Operation(summary = "Crear una factura por ID", description = "Crea una factura{ID}")
    @PostMapping
    public ResponseEntity<FacturaEntity> create(@Valid @RequestBody FacturaDTO dto) {
        FacturaEntity factura = new FacturaEntity();

        PagoEntity pago = new PagoEntity();
        pago.setId(dto.getIdPago());

        factura.setIdFactura(dto.getIdFactura());
        factura.setPago(pago);

        return ResponseEntity.status(201).body(facturaService.save(factura));
    }

    /*
    @PutMapping("/{id}")
    public ResponseEntity<FacturaEntity> update(@PathVariable Long id, @RequestBody FacturaEntity factura) {
        return facturaService.findById(id)
                .map(f -> {
                    factura.setId(id);
                    return ResponseEntity.ok(facturaService.save(factura));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    */
    @Operation(summary = "Actualizar una factura por ID", description = "Actualiza una factura{ID}")
    @PutMapping("/{id}")
    public ResponseEntity<FacturaEntity> update(@PathVariable Long id, @Valid @RequestBody FacturaDTO dto) {
        return facturaService.findById(id)
                .map(existing -> {
                    existing.setIdFactura(dto.getIdFactura());

                    PagoEntity pago = new PagoEntity();
                    pago.setId(dto.getIdPago());
                    existing.setPago(pago);

                    return ResponseEntity.ok(facturaService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Elimina una factura por ID", description = "Elimina una factura{ID}")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facturaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

