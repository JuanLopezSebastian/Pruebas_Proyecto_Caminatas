package co.edu.udistrital.mdp.caminatas.controllers.ContenidoCaminatasControllers;

import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.ContenidoCaminatasDTO.RutaDTO;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.RutaEntity;
import co.edu.udistrital.mdp.caminatas.services.ContenidoCaminatasServices.RutaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Rutas", description = "Operaciones CRUD sobre rutas dentro de mapas")
@RestController
@RequestMapping("/rutas")
@RequiredArgsConstructor
public class RutaController {

    private final RutaService rutaService;

    @Operation(summary = "Crear una nueva ruta")
    @PostMapping
    public ResponseEntity<RutaEntity> createRuta(@Valid @RequestBody RutaDTO dto) {
        RutaEntity creada = rutaService.save(dto);
        return ResponseEntity.status(201).body(creada);
    }

    @Operation(summary = "Listar todas las rutas")
    @GetMapping
    public ResponseEntity<List<RutaEntity>> getAllRutas() {
        return ResponseEntity.ok(rutaService.findAll());
    }

    @Operation(summary = "Obtener una ruta por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<RutaEntity> getRutaById(@PathVariable Long id) {
        return rutaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar una ruta por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRuta(@PathVariable Long id) {
        rutaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}