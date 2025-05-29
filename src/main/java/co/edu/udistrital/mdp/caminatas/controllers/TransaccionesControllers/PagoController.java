package co.edu.udistrital.mdp.caminatas.controllers.TransaccionesControllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.TransaccionesDTO.PagoDTO;
import co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.TransaccionesResponsesDTO.PagoResponseDTO;
import co.edu.udistrital.mdp.caminatas.entities.TransaccionesEntities.PagoEntity;
import co.edu.udistrital.mdp.caminatas.services.TransaccionesServices.PagoService;

import java.util.List;

@Tag(name = "Pagos", description = "Gestión de pagos y simulación de pagos")
@RestController
@RequestMapping("/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @Operation(summary = "Simular un pago (modo MOCK)", description = "Permite registrar un pago ficticio para pruebas o demostraciones.")
    @PostMapping("/simular")
    public ResponseEntity<PagoResponseDTO> simularPago(@RequestBody @Valid PagoDTO dto) {
        PagoEntity pago = pagoService.simularPago(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.toResponseDTO(pago));
    }

    @Operation(summary = "Registrar un pago real o manual", description = "Registra un pago asociado a una factura con cualquier método.")
    @ApiResponse(responseCode = "201", description = "Pago registrado exitosamente")
    @PostMapping
    public ResponseEntity<PagoEntity> registrarPago(@RequestBody @Valid PagoDTO dto) {
        PagoEntity pago = pagoService.registrarPagoMock(dto.getFacturaId(), dto.getMonto(), dto.getMetodo());
        return ResponseEntity.status(HttpStatus.CREATED).body(pago);
    }

    @Operation(summary = "Obtener todos los pagos")
    @GetMapping
    public ResponseEntity<List<PagoEntity>> getAll() {
        return ResponseEntity.ok(pagoService.findAll());
    }

    @Operation(summary = "Buscar un pago por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PagoEntity> getById(@PathVariable Long id) {
        return pagoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Anular pago (soft delete)", description = "Permite eliminar un pago existente por su ID solo si el usuario tiene el rol SUPER_ADMIN.")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> anularPago(@PathVariable Long id) {
        pagoService.delete(id); // hace soft delete
        return ResponseEntity.noContent().build();
    }

}

