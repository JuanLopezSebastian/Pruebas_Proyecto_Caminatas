package co.edu.udistrital.mdp.caminatas.services.ContenidoCaminatasServices;

import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.ContenidoCaminatasDTO.BlogDTO;
import co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.ContenidoCaminatasDTO.BlogResponseDTO;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.BlogEntity;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioNaturalEntity;
import co.edu.udistrital.mdp.caminatas.exceptions.http.NotFoundException;
import co.edu.udistrital.mdp.caminatas.repositories.ContenidoCaminatasRepositories.I_BlogRepository;
import co.edu.udistrital.mdp.caminatas.repositories.UsuariosRepositories.I_UsuarioNaturalRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final I_BlogRepository blogRepository;
    private final I_UsuarioNaturalRepository usuarioNaturalRepository;

    /**
     * Convierte una entidad BlogEntity a un DTO BlogResponseDTO.
     *
     * @param blog la entidad BlogEntity a convertir
     * @return el DTO BlogResponseDTO resultante
     */
    public BlogResponseDTO toResponseDTO(BlogEntity entity) {
        BlogResponseDTO dto = new BlogResponseDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setContenidoTextoBlog(entity.getContenidoTextoBlog());
        dto.setImagenes(entity.getImagenes());
        dto.setVideos(entity.getVideos());

        BlogResponseDTO.AutorDTO autorDTO = new BlogResponseDTO.AutorDTO();
        UsuarioNaturalEntity autor = entity.getAutor();
        autorDTO.setId(autor.getId());
        autorDTO.setNombreUsuario(autor.getNombreUsuario());
        autorDTO.setCorreo(autor.getCorreo());

        dto.setAutor(autorDTO);

        return dto;
    }

    /**
     * Guarda un nuevo blog basado en el DTO proporcionado.
     *
     * @param dto el DTO que contiene los datos del blog a guardar
     * @return la entidad BlogEntity guardada
     */
    public BlogEntity save(BlogDTO dto) {
        UsuarioNaturalEntity autor = usuarioNaturalRepository.findById(dto.getAutorId())
            .orElseThrow(() -> new NotFoundException("Autor no encontrado con ID: " + dto.getAutorId()));

        BlogEntity blog = new BlogEntity();
        blog.setTitle(dto.getTitle());
        blog.setContent(dto.getContent());
        blog.setAutor(autor);
        blog.setImagenes(dto.getImagenes() != null ? dto.getImagenes() : new ArrayList<>());
        blog.setVideos(dto.getVideos() != null ? dto.getVideos() : new ArrayList<>());
        blog.setContenidoTextoBlog(dto.getContenidoTextoBlog());

        return blogRepository.save(blog);
    }
    /**
     * Obtiene todos los blogs guardados en la base de datos.
     *
     * @return una lista de todas las entidades BlogEntity
     */
    public List<BlogEntity> findAll() {
        return blogRepository.findAll();
    }

    /**
     * Busca un blog por su ID.
     *
     * @param id el ID del blog a buscar
     * @return un Optional que contiene la entidad BlogEntity si se encuentra, o vacío si no
     */
    public Optional<BlogEntity> findById(Long id) {
        return blogRepository.findById(id);
    }

    /**
     * Elimina un blog por su ID.
     *
     * @param id el ID del blog a eliminar
     * @throws NotFoundException si no se encuentra un blog con el ID proporcionado
     */
    public void delete(Long id) {
        if (!blogRepository.existsById(id)) {
            throw new NotFoundException("Blog no encontrado con ID: " + id);
        }
        blogRepository.deleteById(id);
    }

}
