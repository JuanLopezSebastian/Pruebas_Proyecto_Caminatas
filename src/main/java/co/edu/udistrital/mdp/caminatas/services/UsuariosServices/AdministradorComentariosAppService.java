package co.edu.udistrital.mdp.caminatas.services.UsuariosServices;

import co.edu.udistrital.mdp.caminatas.config.SecurityConfiguration.JwtUtil;
import co.edu.udistrital.mdp.caminatas.dto.RequestDTO.UsuariosDTO.AdministradorComentariosAppDTO;
import co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.InicioSesionResponseDTO.LoginResponseDTO;
import co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.UsuariosResponsesDTO.UsuarioResponseDTO;
import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.ComentariosEntity;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.AdministradorComentariosAppEntity;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.RolUsuario;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioEntity;
import co.edu.udistrital.mdp.caminatas.exceptions.http.ConflictException;
import co.edu.udistrital.mdp.caminatas.exceptions.http.NotFoundException;
import co.edu.udistrital.mdp.caminatas.exceptions.http.UnauthorizedException;
import co.edu.udistrital.mdp.caminatas.repositories.ContenidoCaminatasRepositories.I_ComentariosRepository;
import co.edu.udistrital.mdp.caminatas.repositories.UsuariosRepositories.I_UsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdministradorComentariosAppService {

    private final I_UsuarioRepository usuarioRepository;
    private final I_ComentariosRepository comentariosRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    
    // Login
    public LoginResponseDTO login(String identificador, String password) {
        Optional<AdministradorComentariosAppEntity> usuarioOpt = usuarioRepository.findAll().stream()
            .filter(u -> u instanceof AdministradorComentariosAppEntity)
            .map(u -> (AdministradorComentariosAppEntity) u)
            .filter(u -> u.getCorreo().equalsIgnoreCase(identificador)
                    || u.getNombreUsuario().equalsIgnoreCase(identificador))
            .findFirst();

        if (usuarioOpt.isEmpty()) {
            throw new UnauthorizedException("Usuario o contraseña inválidos");
        }

        AdministradorComentariosAppEntity usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(password, usuario.getPasswordHash())) {
            throw new UnauthorizedException("Usuario o contraseña inválidos");
        }
        
        if (usuario.getRol() != RolUsuario.ADMIN_COMENTARIOS) {
            throw new UnauthorizedException("Acceso denegado: rol incorrecto");
        }


        String token = jwtUtil.generateToken(usuario.getCorreo(), usuario.getRol());
        return new LoginResponseDTO(usuario.getCorreo(), token, usuario.getRol());
    }


    // Listar todos los comentarios
    public List<ComentariosEntity> findAll() {
        return comentariosRepository.findAll();
    }

    // Comentarios por caminata
    public List<ComentariosEntity> findByCaminataId(Long caminataId) {
        return comentariosRepository.findByCaminata_Id(caminataId);
    }

    // Buscar comentario por id y caminata
    public ComentariosEntity findComentarioByIdAndCaminataId(Long comentarioId, Long caminataId) {
        return comentariosRepository.findByIdAndCaminata_Id(comentarioId, caminataId)
                .orElseThrow(() -> new NoSuchElementException("Comentario no encontrado para esa caminata"));
    }

    // Eliminar comentario por id
    public void deleteById(Long id) {
        if (!comentariosRepository.existsById(id)) {
            throw new NoSuchElementException("Comentario no encontrado");
        }
        comentariosRepository.deleteById(id);
    }
    


    public UsuarioEntity buscarPorCorreo(String correo) {
        return usuarioRepository.findAll().stream()
            .filter(u -> u instanceof AdministradorComentariosAppEntity)
            .filter(u -> u.getCorreo().equalsIgnoreCase(correo))
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Administrador no encontrado"));
    }

    public void eliminarPorCorreo(String correo) {
        UsuarioEntity usuario = buscarPorCorreo(correo);
        usuarioRepository.deleteById(usuario.getId());
    }

    public UsuarioResponseDTO toDTO(UsuarioEntity entity) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(entity.getId());
        dto.setRol(entity.getRol());
        dto.setNombreUsuario(entity.getNombreUsuario());
        dto.setCorreo(entity.getCorreo());
        dto.setCedula(entity.getCedula());
        dto.setTelefono(entity.getTelefono());
        return dto;
    }
    public UsuarioResponseDTO actualizarPerfil(String correo, AdministradorComentariosAppDTO dto) {
        UsuarioEntity entity = buscarPorCorreo(correo);

        entity.setNombreUsuario(dto.getNombreUsuario());
        entity.setCorreo(dto.getCorreo());
        entity.setTelefono(dto.getTelefono());

        usuarioRepository.save(entity);

        return toDTO(entity);
    }


}
