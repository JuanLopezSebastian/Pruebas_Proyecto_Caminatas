package co.edu.udistrital.mdp.caminatas.services.UsuariosServices;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.caminatas.config.SecurityConfiguration.JwtUtil;
import co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.InicioSesionResponseDTO.LoginResponseDTO;
import co.edu.udistrital.mdp.caminatas.dto.ResponsesDTO.UsuariosResponsesDTO.UsuarioNaturalResponseDTO;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.RolUsuario;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioEntity;
import co.edu.udistrital.mdp.caminatas.entities.UsuariosEntities.UsuarioNaturalEntity;
import co.edu.udistrital.mdp.caminatas.exceptions.http.ConflictException;
import co.edu.udistrital.mdp.caminatas.exceptions.http.UnauthorizedException;
import co.edu.udistrital.mdp.caminatas.repositories.UsuariosRepositories.I_UsuarioRepository;

@Service
public class UsuarioNaturalService {

    private final I_UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioNaturalService(I_UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public UsuarioNaturalResponseDTO toResponseDTO(UsuarioNaturalEntity entity) {
        return new UsuarioNaturalResponseDTO(
            entity.getId(),
            entity.getRol(),
            entity.getNombreUsuario(),
            entity.getCorreo(),
            entity.getCedula(),
            entity.getTelefono()
        );
    }
    
    public LoginResponseDTO login(String identificador, String password) {
        Optional<UsuarioNaturalEntity> usuarioOpt = usuarioRepository.findAll()
            .stream()
            .filter(u -> u instanceof UsuarioNaturalEntity)
            .map(u -> (UsuarioNaturalEntity) u)
            .filter(u -> u.getCorreo().equalsIgnoreCase(identificador)
                    || u.getNombreUsuario().equalsIgnoreCase(identificador))
            .findFirst();

        if (usuarioOpt.isEmpty()) {
            throw new UnauthorizedException("Usuario o contraseña inválidos");
        }

        UsuarioNaturalEntity usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(password, usuario.getPasswordHash())) {
            throw new UnauthorizedException("Usuario o contraseña inválidos");
        }

        // Usa correo y rol en el token
        String token = jwtUtil.generateToken(usuario.getCorreo(), usuario.getRol());

        // Retorna los 3 campos requeridos
        return new LoginResponseDTO(usuario.getCorreo(), token, usuario.getRol());
    }



    public List<UsuarioNaturalEntity> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .filter(u -> u instanceof UsuarioNaturalEntity)
                .map(u -> (UsuarioNaturalEntity) u)
                .toList();
    }

    public Optional<UsuarioNaturalEntity> findById(Long id) {
        return usuarioRepository.findById(id)
                .filter(u -> u instanceof UsuarioNaturalEntity)
                .map(u -> (UsuarioNaturalEntity) u);
    }

    public UsuarioNaturalEntity save(UsuarioNaturalEntity usuario) {
        if (usuario.getRol() == null) {
            throw new IllegalArgumentException("El campo 'rol' no puede ser nulo");
        }

        boolean isUpdate = usuario.getId() != null;

        // Evitar que se cree un usuario con rol no permitido
        if (!isUpdate) {
            if (usuario.getRol() == RolUsuario.SUPER_ADMIN || usuario.getRol() == RolUsuario.ADMIN_COMENTARIOS) {
                throw new IllegalArgumentException("No está permitido crear usuarios con estos roles");
            }

            if (usuario.getRol() != RolUsuario.NATURAL) {
                throw new IllegalArgumentException("Este endpoint solo permite crear usuarios con rol NATURAL");
            }
        }

        // Validaciones de unicidad
        for (UsuarioEntity existente : usuarioRepository.findAll()) {
            boolean esOtro = !isUpdate || !existente.getId().equals(usuario.getId());

            if (esOtro && existente.getCorreo().equalsIgnoreCase(usuario.getCorreo())) {
                throw new ConflictException("Ya existe un usuario con el correo: " + usuario.getCorreo());
            }

            if (esOtro && existente.getCedula().equals(usuario.getCedula())) {
                throw new ConflictException("Ya existe un usuario con la cédula: " + usuario.getCedula());
            }

            if (esOtro && existente.getNombreUsuario().equalsIgnoreCase(usuario.getNombreUsuario())) {
                throw new ConflictException("Ya existe un usuario con el nombre: " + usuario.getNombreUsuario());
            }
        }

        return usuarioRepository.save(usuario);
    }


    

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
}

