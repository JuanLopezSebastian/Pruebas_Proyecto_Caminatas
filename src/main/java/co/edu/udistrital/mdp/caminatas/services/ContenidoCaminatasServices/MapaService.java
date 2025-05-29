package co.edu.udistrital.mdp.caminatas.services.ContenidoCaminatasServices;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.ContenidoCaminatasDTO.CoordenadaDTO;
import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.ContenidoCaminatasDTO.MapaDTO;
import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.ContenidoCaminatasDTO.RutaDTO;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.Coordenada;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.MapaEntity;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.RutaEntity;
import co.edu.udistrital.mdp.caminatas.repositories.ContenidoCaminatasRepositories.I_MapaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MapaService {

    private final I_MapaRepository mapaRepository;

    public MapaEntity save(MapaDTO dto) {
        validarCantidadRutas(dto.getRutas());

        MapaEntity mapa = new MapaEntity();
        mapa.setDescripcion(dto.getDescripcion());

        if (dto.getCoordenadasGenerales() != null) {
            mapa.setCoordenadasGenerales(convertirCoordenadas(dto.getCoordenadasGenerales()));
        }

        mapa.setRutas(convertirRutas(dto.getRutas()));
        return mapaRepository.save(mapa);
    }

    public List<MapaEntity> findAll() {
        return mapaRepository.findAll();
    }

    public Optional<MapaEntity> findById(Long id) {
        return mapaRepository.findById(id);
    }

    public void delete(Long id) {
        mapaRepository.deleteById(id);
    }

    // ---------- Métodos auxiliares privados ------------------

    private void validarCantidadRutas(List<RutaDTO> rutas) {
        if (rutas == null || rutas.isEmpty() || rutas.size() > 5) {
            throw new IllegalArgumentException("El mapa debe tener entre 1 y 5 rutas.");
        }
    }

    private List<Coordenada> convertirCoordenadas(List<CoordenadaDTO> coordenadasDTO) {
        return coordenadasDTO.stream()
            .map(c -> new Coordenada(c.getLatitud(), c.getLongitud()))
            .toList();
    }

    private List<RutaEntity> convertirRutas(List<RutaDTO> rutasDTO) {
        return rutasDTO.stream().map(r -> {
            RutaEntity ruta = new RutaEntity();
            ruta.setNombreRuta(r.getNombreRuta());
            ruta.setDescripcionRuta(r.getDescripcionRuta());
            ruta.setCoordenadas(convertirCoordenadas(r.getCoordenadas()));
            return ruta;
        }).toList();
    }
}
