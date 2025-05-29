package co.edu.udistrital.mdp.caminatas.services.ContenidoCaminatasServices;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.ContenidoCaminatasDTO.CoordenadaDTO;
import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.ContenidoCaminatasDTO.RutaDTO;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.Coordenada;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.RutaEntity;
import co.edu.udistrital.mdp.caminatas.exceptions.http.NotFoundException;
import co.edu.udistrital.mdp.caminatas.repositories.ContenidoCaminatasRepositories.I_RutaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RutaService {

    private final I_RutaRepository rutaRepository;

    public RutaEntity save(RutaDTO dto) {
        RutaEntity ruta = new RutaEntity();
        ruta.setNombreRuta(dto.getNombreRuta());
        ruta.setDescripcionRuta(dto.getDescripcionRuta());

        List<Coordenada> coordenadas = buildCoordenadas(dto.getCoordenadas());
        if (coordenadas == null || coordenadas.size() < 2) {
            throw new IllegalArgumentException("Una ruta debe tener al menos dos coordenadas.");
        }

        ruta.setCoordenadas(coordenadas);
        return rutaRepository.save(ruta);
    }

    public List<RutaEntity> findAll() {
        return rutaRepository.findAll();
    }

    public Optional<RutaEntity> findById(Long id) {
        return rutaRepository.findById(id);
    }

    public void delete(Long id) {
        if (!rutaRepository.existsById(id)) {
            throw new NotFoundException("Ruta no encontrada con ID: " + id);
        }
        rutaRepository.deleteById(id);
    }

    // Método reutilizable para convertir DTOs en coordenadas embebidas
    private List<Coordenada> buildCoordenadas(List<CoordenadaDTO> coordenadaDTOs) {
        return coordenadaDTOs.stream()
                .map(dto -> new Coordenada(dto.getLatitud(), dto.getLongitud()))
                .toList();
    }
}
