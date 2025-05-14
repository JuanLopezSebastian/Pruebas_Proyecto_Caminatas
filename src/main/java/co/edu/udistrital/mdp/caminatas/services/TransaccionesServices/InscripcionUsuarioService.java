package co.edu.udistrital.mdp.caminatas.services.TransaccionesServices;


import co.edu.udistrital.mdp.caminatas.entities.TransaccionesEntities.InscripcionUsuarioEntity;
import co.edu.udistrital.mdp.caminatas.repositories.TransaccionesRepositories.I_InscripcionUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InscripcionUsuarioService {

    @Autowired
    private I_InscripcionUsuarioRepository inscripcionUsuarioRepository;

    public List<InscripcionUsuarioEntity> findAll() {
        return inscripcionUsuarioRepository.findAll();
    }

    public Optional<InscripcionUsuarioEntity> findById(Long id) {
        return inscripcionUsuarioRepository.findById(id);
    }

    public InscripcionUsuarioEntity save(InscripcionUsuarioEntity inscripcion) {
        return inscripcionUsuarioRepository.save(inscripcion);
    }

    public void delete(Long id) {
        inscripcionUsuarioRepository.deleteById(id);
    }
}

