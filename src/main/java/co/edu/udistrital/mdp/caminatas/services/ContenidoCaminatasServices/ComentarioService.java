package co.edu.udistrital.mdp.caminatas.services.ContenidoCaminatasServices;

import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.ComentariosEntity;
import co.edu.udistrital.mdp.caminatas.repositories.ContenidoCaminatasRepositories.I_ComentariosRepository;
import co.edu.udistrital.mdp.caminatas.repositories.InscripcionesRepositories.I_InscripcionUsuarioRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ComentarioService {

    private static final Logger logger = LoggerFactory.getLogger(ComentarioService.class);
    private final I_ComentariosRepository comentarioRepository;
    private final I_InscripcionUsuarioRepository inscripcionRepository;

    // Constructor para inyección de dependencias
    public ComentarioService(I_ComentariosRepository comentarioRepository, I_InscripcionUsuarioRepository inscripcionRepository) {
        this.comentarioRepository = comentarioRepository;
        this.inscripcionRepository = inscripcionRepository;
    }

    /**
     * Guarda un nuevo comentario si el usuario ha participado en la caminata.
     *
     * @param comentario el comentario a guardar
     * @return el comentario guardado
     * @throws IllegalArgumentException si el usuario no ha participado en la caminata
     */
    public ComentariosEntity save(ComentariosEntity comentario) {
        Long usuarioId = comentario.getAutor().getId();
        Long caminataId = comentario.getCaminata().getId();

        // Validar que haya participado
        boolean participo = inscripcionRepository.existsByUsuario_IdAndCaminata_Id(usuarioId, caminataId);

        if (!participo) {
            throw new IllegalArgumentException("El usuario no puede comentar esta caminata porque no está inscrito.");
        }

        return comentarioRepository.save(comentario);
    }

    /**
     * Obtiene todos los comentarios.
     *
     * @return lista de todos los comentarios
     */
    public List<ComentariosEntity> findAll() {
        return comentarioRepository.findAll();
    }

    /**
     * Busca un comentario por su ID.
     *
     * @param id el ID del comentario
     * @return un Optional que contiene el comentario si se encuentra, o vacío si no
     */
    public Optional<ComentariosEntity> findById(Long id) {
        return comentarioRepository.findById(id);
    }

    /**
     * Busca comentarios por el ID de la caminata.
     *
     * @param caminataId el ID de la caminata
     * @return lista de comentarios asociados a la caminata
     */
    public List<ComentariosEntity> findByCaminataId(Long caminataId) {
        return comentarioRepository.findByCaminata_Id(caminataId);
    }

    /**
     * Elimina un comentario por su ID.
     *
     * @param id el ID del comentario a eliminar
     * @throws IllegalArgumentException si no se encuentra un comentario con el ID proporcionado
     */
    public void deleteById(Long id) {
        if (!comentarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Comentario no encontrado");
        }
        comentarioRepository.deleteById(id);
        logger.info("🗑️ Comentario eliminado por admin. ID: {}", id);
    }

}

