package co.edu.udistrital.mdp.caminatas.services.ContenidoCaminatasServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.ContenidoCaminatasDTO.GaleriaDTO;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.GaleriaEntity;
import co.edu.udistrital.mdp.caminatas.exceptions.http.NotFoundException;
import co.edu.udistrital.mdp.caminatas.repositories.ContenidoCaminatasRepositories.I_GaleriaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GaleriaService {

    private final I_GaleriaRepository galeriaRepository;

    public GaleriaEntity save(GaleriaDTO dto) {
        GaleriaEntity galeria = new GaleriaEntity();
        galeria.setImagenPrincipal(dto.getImagenPrincipal());
        galeria.setImagenesGaleria(new ArrayList<>(dto.getImagenesGaleria()));
        return galeriaRepository.save(galeria);
    }

    public List<GaleriaEntity> findAll() {
        return galeriaRepository.findAll();
    }

    public Optional<GaleriaEntity> findById(Long id) {
        return galeriaRepository.findById(id);
    }

    public void delete(Long id) {
        if (!galeriaRepository.existsById(id)) {
            throw new NotFoundException("Galería no encontrada con ID: " + id);
        }
        galeriaRepository.deleteById(id);
    }
}
