package co.edu.udistrital.mdp.caminatas.services.CaminatasServices;

import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.CaminatasDTO.CaminataDTO;
import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.ContenidoCaminatasDTO.CoordenadaDTO;
import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.ContenidoCaminatasDTO.GaleriaDTO;
import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.ContenidoCaminatasDTO.MapaDTO;
import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.ContenidoCaminatasDTO.RutaDTO;
import co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.CaminatasResponsesDTO.CaminataResponseDTO;
import co.edu.udistrital.mdp.caminatas.entities.CaminatasEntities.CaminataEntity;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.GaleriaEntity;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.MapaEntity;
import co.edu.udistrital.mdp.caminatas.exceptions.http.NotFoundException;
import co.edu.udistrital.mdp.caminatas.repositories.CaminatasRepositories.I_CaminataRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CaminataService {

    private final I_CaminataRepository caminataRepository;

    /**
     * Crea una nueva caminata a partir del DTO.
     */
    public CaminataEntity createFromDTO(CaminataDTO dto) {
        CaminataEntity caminata = toEntity(dto);
        return caminataRepository.save(caminata);
    }

    /**
     * Actualiza una caminata existente con los datos del DTO.
     */
    public Optional<CaminataEntity> updateFromDTO(Long id, CaminataDTO dto) {
        return caminataRepository.findById(id)
                .map(existing -> {
                    updateEntityFromDTO(existing, dto);
                    return caminataRepository.save(existing);
                });
    }

    /**
     * Obtiene todas las caminatas.
     */
    public List<CaminataEntity> findAll() {
        return caminataRepository.findAll();
    }

    /**
     * Busca una caminata por su ID.
     */
    public Optional<CaminataEntity> findById(Long id) {
        return caminataRepository.findById(id);
    }

    /**
     * Elimina una caminata por su ID.
     */
    public void delete(Long id) {
        if (!caminataRepository.existsById(id)) {
            throw new NotFoundException("Caminata no encontrada con ID: " + id);
        }
        caminataRepository.deleteById(id);
    }

    // 🔁 Conversión DTO -> Entity
    private CaminataEntity toEntity(CaminataDTO dto) {
        CaminataEntity caminata = new CaminataEntity();
        updateEntityFromDTO(caminata, dto);
        return caminata;
    }
    public CaminataResponseDTO toResponseDTO(CaminataEntity entity) {
        CaminataResponseDTO dto = new CaminataResponseDTO();

        dto.setId(entity.getId());
        dto.setNombreCaminata(entity.getNombreCaminata());
        dto.setCostoCaminata(entity.getCostoCaminata());
        dto.setPatrocinador(entity.getPatrocinador());
        dto.setFecha(entity.getFecha());
        dto.setHora(entity.getHora());
        dto.setLugar(entity.getLugar());
        dto.setDuracion(entity.getDuracion());
        dto.setDescripcion(entity.getDescripcion());
        dto.setDificultad(entity.getDificultad());
        dto.setItinerario(entity.getItinerario());
        dto.setRecomendaciones(entity.getRecomendaciones());

        // Relacionales
        if (entity.getMapa() != null) {
            dto.setMapa(mapToDTO(entity.getMapa()));
        }
        if (entity.getGaleria() != null) {
            dto.setGaleria(mapGaleriaToDTO(entity.getGaleria()));
        }

        dto.setTotalComentarios(entity.getComentarios() != null ? entity.getComentarios().size() : 0);

        return dto;
    }


    // 🔁 Reutiliza lógica para actualizar una entidad desde DTO
    private void updateEntityFromDTO(CaminataEntity entity, CaminataDTO dto) {
        entity.setNombreCaminata(dto.getNombreCaminata());
        entity.setCostoCaminata(dto.getCostoCaminata());
        entity.setPatrocinador(dto.getPatrocinador());
        entity.setFecha(dto.getFecha());
        entity.setHora(dto.getHora());
        entity.setLugar(dto.getLugar());
        entity.setDuracion(dto.getDuracion());
        entity.setDescripcion(dto.getDescripcion());
        entity.setDificultad(dto.getDificultad());
        entity.setItinerario(dto.getItinerario());
        entity.setRecomendaciones(dto.getRecomendaciones());
    }
    // 🔁 Conversión de Mapa y Galería a DTO
    private MapaDTO mapToDTO(MapaEntity mapa) {
        MapaDTO dto = new MapaDTO();
        dto.setDescripcion(mapa.getDescripcion());

        if (mapa.getCoordenadasGenerales() != null) {
            dto.setCoordenadasGenerales(
                mapa.getCoordenadasGenerales().stream()
                    .map(c -> new CoordenadaDTO(c.getLatitud(), c.getLongitud()))
                    .toList()
            );
        }

        if (mapa.getRutas() != null) {
            dto.setRutas(
                mapa.getRutas().stream().map(r -> {
                    RutaDTO rDto = new RutaDTO();
                    rDto.setNombreRuta(r.getNombreRuta());
                    rDto.setDescripcionRuta(r.getDescripcionRuta());
                    rDto.setCoordenadas(
                        r.getCoordenadas().stream()
                            .map(coord -> new CoordenadaDTO(coord.getLatitud(), coord.getLongitud()))
                            .toList()
                    );
                    return rDto;
                }).toList()
            );
        }

        return dto;
    }

    private GaleriaDTO mapGaleriaToDTO(GaleriaEntity galeria) {
        GaleriaDTO dto = new GaleriaDTO();
        dto.setImagenPrincipal(galeria.getImagenPrincipal());
        dto.setImagenesGaleria(galeria.getImagenesGaleria());
        return dto;
    }


}
