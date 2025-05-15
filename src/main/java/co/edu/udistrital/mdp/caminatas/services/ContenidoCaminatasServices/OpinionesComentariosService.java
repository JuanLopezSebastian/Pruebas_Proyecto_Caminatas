package co.edu.udistrital.mdp.caminatas.services.ContenidoCaminatasServices;

import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.OpinionesComentariosEntity;
import co.edu.udistrital.mdp.caminatas.repositories.ContenidoCaminatasRepositories.I_OpinionesComentariosRepository;
import co.edu.udistrital.mdp.caminatas.repositories.TransaccionesRepositories.I_InscripcionUsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OpinionesComentariosService {

    @Autowired
    private I_OpinionesComentariosRepository comentarioRepository;

    @Autowired
    private I_InscripcionUsuarioRepository inscripcionRepository;


    public List<OpinionesComentariosEntity> findAll() {
        return comentarioRepository.findAll();
    }

    public Optional<OpinionesComentariosEntity> findById(Long id) {
        return comentarioRepository.findById(id);
    }

    public OpinionesComentariosEntity save(OpinionesComentariosEntity comentario) {
        Long usuarioId = comentario.getAutor().getId();
        Long caminataId = comentario.getCaminata().getId();

        // Validar que haya participado
        boolean participo = inscripcionRepository.existsByUsuario_IdAndCaminata_Id(usuarioId, caminataId);

        if (!participo) {
            throw new IllegalArgumentException("El usuario no puede comentar esta caminata porque no está inscrito.");
        }

        return comentarioRepository.save(comentario);
    }

    public void delete(Long id) {
        comentarioRepository.deleteById(id);
    }
}
