package co.edu.udistrital.mdp.caminatas.controllers.CaminatasControllers;

import co.edu.udistrital.mdp.caminatas.dto.CaminatasDTO.CaminataDTO;
import co.edu.udistrital.mdp.caminatas.entities.CaminatasEntities.CaminataEntity;
import co.edu.udistrital.mdp.caminatas.services.CaminatasServices.CaminataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Caminatas", description = "Gestión de caminatas disponibles")
@RestController
@RequestMapping("/caminatas")
public class CaminataController {

    @Autowired
    private CaminataService caminataService;

    @Operation(summary = "Obtener una lista de todas las caminatas", description = "Obtiene una lista de todas las caminatas registradas")
    @GetMapping
    public ResponseEntity<List<CaminataEntity>> getAll() {
        return ResponseEntity.ok(caminataService.findAll());
    }

    @Operation(summary = "Obtener una caminata por ID", description = "Obtiene una caminata{ID}")
    @GetMapping("/{id}")
    public ResponseEntity<CaminataEntity> getById(@PathVariable Long id) {
        return caminataService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    /*
    @PostMapping
    public ResponseEntity<CaminataEntity> create(@RequestBody CaminataEntity caminata) {
        return ResponseEntity.status(201).body(caminataService.save(caminata));
    }
    */
    @Operation(summary = "Crear una caminata por ID", description = "Crea una caminata{ID}")
    @PostMapping
    public ResponseEntity<CaminataEntity> create(@Valid @RequestBody CaminataDTO dto) {
        CaminataEntity caminata = new CaminataEntity();

        caminata.setNombreCaminata(dto.getNombreCaminata());
        caminata.setCostoCaminata(dto.getCostoCaminata());
        caminata.setPatrocinador(dto.getPatrocinador());
        caminata.setFecha(dto.getFecha());
        caminata.setHora(dto.getHora());
        caminata.setLugar(dto.getLugar());
        caminata.setDuracion(dto.getDuracion());
        caminata.setDescripcion(dto.getDescripcion());
        caminata.setDificultad(dto.getDificultad());
        caminata.setItinerario(dto.getItinerario());
        caminata.setRecomendaciones(dto.getRecomendaciones());

        return ResponseEntity.status(201).body(caminataService.save(caminata));
    }

    @Operation(summary = "Actualizar una caminata por ID", description = "Actualiza una caminata{ID}")
    @PutMapping("/{id}")
    public ResponseEntity<CaminataEntity> update(@PathVariable Long id, @RequestBody CaminataEntity caminata) {
        return caminataService.findById(id)
                .map(c -> {
                    caminata.setId(id);
                    return ResponseEntity.ok(caminataService.save(caminata));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar una caminata por ID", description = "Elimina una caminata{ID}")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        caminataService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

